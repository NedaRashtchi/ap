package ap.Project;

import java.util.Scanner;

public class Menu {
    public int mainMenu(Scanner input) {
        System.out.println("\nWelcome to the Library\nWhat would you like to do?");
        System.out.println("1. Login as Student ");
        System.out.println("2. Register as Student");
        System.out.println("3. Login as Librarian ");
        System.out.println("4. Login as Manager ");
        System.out.println("5. Exit");
        int choice = input.nextInt();
        input.nextLine(); // return input.nextInt(); wrong
        return choice;
    }

    public int studentMenu(Scanner input) {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. View All Books");
        System.out.println("2. Search Book");
        System.out.println("3. Borrow or Return Book");
        System.out.println("4. Unreturned Books");
        System.out.println("5. Back to Main Menu");
        int choice = input.nextInt();
        input.nextLine();
        return choice;
    }

    public int librarianMenu(Scanner input) {
        System.out.println("\nwhat would you like to do?");
        System.out.println("1. Add Book");
        System.out.println("2. Edit Personal Info");
        System.out.println("3. View and Approve Requests");
        System.out.println("4. Back to Main Menu");
        int choice = input.nextInt();
        input.nextLine();
        return choice;
    }

    public int managerMenu(Scanner input) {
        System.out.println("what would you like to do?");
        System.out.println("1. Add Librarian");
        System.out.println("2. View All Books");
        System.out.println("3. View Delayed Returns");
        System.out.println("4. View Librarian Activity Report");
        System.out.println("5. View Popular Books");
        System.out.println("6. Back to Main Menu");
        int choice = input.nextInt();
        input.nextLine();
        return choice;
    }
}
