import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        try (Printer printer = new Printer(Colors.GREEN, new Integer[]{7, 5}, 7, '0')){
            System.out.print("Enter an English word: ");
            printer.print(sc.nextLine());
        }

        System.out.print("Enter an English word: ");
        Printer.print(sc.nextLine(), Colors.GREEN, new Integer[]{7, 5}, 7, '9');
        sc.close();
        System.out.println("Done!");
    }
}