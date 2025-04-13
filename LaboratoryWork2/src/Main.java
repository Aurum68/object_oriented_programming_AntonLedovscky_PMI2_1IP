import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        try (Printer printer = new Printer(Colors.BLUE, new Integer[]{7, 5}, 5, '0')){
            System.out.print("Enter an English word: ");
            printer.print(sc.nextLine());
            printer.print(sc.nextLine());
        }

        System.out.print("Enter an English word: ");
        Printer.print(sc.nextLine(), Colors.RED, new Integer[]{7, 5}, 7, '9');
        sc.close();
        System.out.println("Done!");
    }
}