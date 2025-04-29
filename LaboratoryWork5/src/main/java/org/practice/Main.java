package org.practice;

import org.practice.record.User;
import org.practice.repository.UserRepository;
import org.practice.service.AuthService;

import java.util.List;
import java.util.Scanner;

public class Main {
    static UserRepository userRepository = new UserRepository("src/main/resources/users.json", User.class);
    static AuthService authService = new AuthService(userRepository);

    private static final Scanner scanner = new Scanner(System.in);

    private static void mainMenu(){
        System.out.println("Hello!");
        System.out.println("Current user: " + authService.getCurrentUser());
        if (authService.getCurrentUser() == null) {
            System.out.println("Choose an option:\n" +
                    "sign in (input 'in')\tquit (input 'q')");
            return;
        }
        System.out.println("Choose next option:\n" +
                "sign out (input 'out')\tchange user (input 'cu')\tuser settings (input 'us')\tquit (input 'q')");
    }

    private static void signInMenu(int id){


        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your login: ");
        String login = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("If you want, enter your email address: ");
        String email = scanner.nextLine();
        email = email.isEmpty() ? null : email;
        System.out.print("If you want, enter your address: ");
        String address = scanner.nextLine();
        address = address.isEmpty() ? null : address;

        while (userRepository.getById(id) != null) {id++;}
        User user = new User(id, name, login, password, email, address);

        if (authService.isAuthorized(user)) {authService.signIn(user);}
        else {authService.update(userRepository.getByLogin(login));}

        System.out.println("Current user: " + authService.getCurrentUser());
        System.out.println("Choose next option:\n" +
                "sign out (input 'out')\tchange user (input 'cu')\tuser settings (input 'us')\tquit (input 'q')");
    }

    private static void changeUserMenu(){
        System.out.println("Current user: " + authService.getCurrentUser());
        List<User> users = userRepository.getAll();
        System.out.println("Accessible accounts:");
        if (users.size() == 1 && users.getFirst().equals(authService.getCurrentUser())){
            System.out.println("There aren't accessible accounts. You can sign in");
            signInMenu(1);
            return;
        }

        for (User user : users){
            if (!authService.getCurrentUser().equals(user)){System.out.println(user);}
        }

        System.out.print("Enter login, what you want to sign in or Enter 'in' to create new account: ");
        String login = scanner.nextLine();
        if (login.equals("in")){
            signInMenu(1);
            return;
        }
        if (authService.isAuthorized(userRepository.getByLogin(login))){
            throw new IllegalArgumentException("Invalid login, user does not exist");
        }

        authService.update(userRepository.getByLogin(login));
        System.out.println("Current user: " + authService.getCurrentUser());
        System.out.println("Choose next option:\n" +
                "sign out (input 'out')\tchange user (input 'cu')\tuser settings (input 'us')\tquit (input 'q')");
    }

    private static void userSettingsMenu(){
        String setting = "";
        while (!setting.equals("y")) {
            System.out.println("Current user: " + authService.getCurrentUser());
            System.out.println("What do you want to change? ");
            setting = scanner.nextLine();
            User user;
            switch (setting) {
                case "name":
                    System.out.print("Enter new name: ");
                    String name = scanner.nextLine();
                    user = userRepository.update(new User(authService.getCurrentUser().getId(),
                            name,
                            authService.getCurrentUser().login(),
                            authService.getCurrentUser().password(),
                            authService.getCurrentUser().email(),
                            authService.getCurrentUser().address()));
                    break;
                case "login":
                    System.out.print("Enter new login: ");
                    String login = scanner.nextLine();
                    user = userRepository.update(new User(authService.getCurrentUser().getId(),
                            authService.getCurrentUser().name(),
                            login,
                            authService.getCurrentUser().password(),
                            authService.getCurrentUser().email(),
                            authService.getCurrentUser().address()));
                    break;
                case "password":
                    System.out.print("Enter new password: ");
                    String password = scanner.nextLine();
                    user = userRepository.update(new User(authService.getCurrentUser().getId(),
                            authService.getCurrentUser().name(),
                            authService.getCurrentUser().login(),
                            password,
                            authService.getCurrentUser().email(),
                            authService.getCurrentUser().address()));
                    break;
                case "email":
                    System.out.print("Enter new email: ");
                    String email = scanner.nextLine();
                    user = userRepository.update(new User(authService.getCurrentUser().getId(),
                            authService.getCurrentUser().name(),
                            authService.getCurrentUser().login(),
                            authService.getCurrentUser().password(),
                            email,
                            authService.getCurrentUser().address()));
                    break;
                case "address":
                    System.out.print("Enter new address: ");
                    String address = scanner.nextLine();
                    user = userRepository.update(new User(authService.getCurrentUser().getId(),
                            authService.getCurrentUser().name(),
                            authService.getCurrentUser().login(),
                            authService.getCurrentUser().password(),
                            authService.getCurrentUser().email(),
                            address));
                    break;
                default:
                    System.out.println("Unknown option");
                    user = authService.getCurrentUser();
            }

            authService.update(user);
            System.out.println("Current user: " + authService.getCurrentUser());
            System.out.println("""
                    That's all?
                    yes (input 'y') if you finished the change
                    no  (input 'n') if you want to continue to change""");
            setting = scanner.nextLine();
        }
        System.out.println("Current user: " + authService.getCurrentUser());
        System.out.println("Choose next option:\n" +
                "sign out (input 'out')\tchange user (input 'cu')\tuser settings (input 'us')\tquit (input 'q')");
    }

    public static void main(String[] args) {
        int id = 1;
        mainMenu();
        String input = scanner.nextLine();
        while (!input.equals("q")){
            if (authService.getCurrentUser() != null) {
                switch (input){
                    case "out":
                        authService.signOut(authService.getCurrentUser());
                        mainMenu();
                        input = scanner.nextLine();
                        break;
                    case "cu":
                        changeUserMenu();
                        input = scanner.nextLine();
                        break;
                    case "us":
                        userSettingsMenu();
                        input = scanner.nextLine();
                        break;
                    default:
                        System.out.println("Unknown option. Try again.");
                        input = scanner.nextLine();
                        break;
                }
                continue;
            }
            if (input.equals("in")) {
                signInMenu(id);
                input = scanner.nextLine();
                id++;
            }
        }
    }
}