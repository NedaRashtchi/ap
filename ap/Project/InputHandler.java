package ap.Project;

import java.util.Scanner;

public class InputHandler {

    Scanner scanner = new Scanner(System.in);

    public int getInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public String getLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}

