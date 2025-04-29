package org.practice.service;

import org.practice.record.User;
import org.practice.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class AuthService implements IAuthService{

    private final String DIRECTORY = "src/main/resources/";
    private final String filename = "lastUser.txt";
    private User currentUser;
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        if (!isThereFile()){
            createFile();
        }
        if (!this.userRepository.getAll().isEmpty()) {
            String login = readFile().trim();
            this.currentUser = this.userRepository.getByLogin(login);
        }

    }

    private void rememberLastUser(){
        writeFile(currentUser.login());
    }

    private boolean isThereFile() {
        File folder = new File(this.DIRECTORY);

        if (!folder.exists()){
            boolean folderCreatedSuccessful = folder.mkdirs();
            if (!folderCreatedSuccessful) {
                throw new RuntimeException("Failed to create folder " + folder.getAbsolutePath());
            }
        }

        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            System.err.printf("Folder" + DIRECTORY + " not found");
            return false;
        }
        for (File file : listOfFiles) {
            if (file.getName().equals(filename)) {
                return true;
            }
        }
        return false;
    }

    private void createFile() {
        Path filePath = Paths.get(this.DIRECTORY, filename);
        try {
            Files.createFile(filePath);
        }catch (IOException e) {
            System.out.println("File has already been created");
        }
    }

    private void writeFile(String text) {
        if (text == null) throw new NullPointerException();

        List<String> lines = Arrays.asList(text.split("\n"));
        try {
            Files.write(Paths.get(this.DIRECTORY, filename), lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFile() {
        try {
            return Files.readString(Paths.get(this.DIRECTORY, filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(User user) {
        this.currentUser = user;
        rememberLastUser();
    }

    @Override
    public void signIn(User user) {
        this.currentUser = user;
        if (this.isAuthorized(user)){
            userRepository.add(user);
        }
        rememberLastUser();
    }

    @Override
    public void signOut(User user) {
        if (this.currentUser == user) {
            this.currentUser = null;
        }
    }

    @Override
    public boolean isAuthorized(User user) {
        if (user == null) {
            throw new NullPointerException("user is null");
        }
        return this.userRepository.getByLogin(user.login()) == null &&
                this.userRepository.getById(user.id()) == null;
    }

    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }
}
