package ap.Project;

import java.util.Scanner;

public class InputHandler {
    public int getInt(String prompt, Scanner scanner) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public String getLine(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}

