package org.example.handler;

import java.io.File;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Arrays;

import java.io.IOException;

public class FileHandler implements ILogHandler{

    private final String fileName;
    private final String directory = "src/main/logs";

    public FileHandler(String fileName) {
        this.fileName = fileName;
        if (!isThereFile(fileName)){
            createFile(this.fileName);
        }
    }

    private boolean isThereFile(String fileName) {
        File folder = new File(this.directory);

        if (!folder.exists()){
            boolean folderCreatedSuccessful = folder.mkdirs();
            if (!folderCreatedSuccessful) {
                throw new RuntimeException("Failed to create folder " + folder.getAbsolutePath());
            }
        }

        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            System.err.printf("Folder" + directory + " not found");
            return false;
        }
        for (File file : listOfFiles) {
            if (file.getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    private void createFile(String fileName) {
        Path filePath = Paths.get(this.directory, fileName);
        try {
            Files.createFile(filePath);
        }catch (IOException e) {
            System.out.println("File has already been created");
        }
    }

    private void writeFile(String fileName, String text) {
        List<String> lines = Arrays.asList(text.split("\n"));
        try {
            Files.write(Paths.get(this.directory, fileName), lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handle(String text) {
        writeFile(this.fileName, text);
    }
}
