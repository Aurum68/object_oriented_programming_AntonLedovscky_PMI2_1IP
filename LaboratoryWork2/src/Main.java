import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        try (Printer printer = new Printer("red", 7, '/')){
            System.out.print("Enter an English word: ");
            printer.print(sc.nextLine());
        }
        sc.close();
        System.out.println("Done!");
    }
}