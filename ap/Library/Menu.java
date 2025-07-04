package ap.Library;

import ap.Library.SystemHandleAndStorage.InputHandler;

public class Menu {
    public int mainMenu(InputHandler inputHandler) {
        System.out.println("\nWelcome to the Library\nWhat would you like to do?");
        System.out.println("1. Login as Student ");
        System.out.println("2. Register as Student");
        System.out.println("3. Login as Librarian ");
        System.out.println("4. Login as Manager ");
        System.out.println("5. Exit");

        return inputHandler.getInt("");
    }

    public int studentMenu(InputHandler inputHandler) {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. View All Books");
        System.out.println("2. Search Book");
        System.out.println("3. Borrow or Return Book");
        System.out.println("4. Unreturned Books");
        System.out.println("5. View Borrowed Books");
        System.out.println("6. Back to Main Menu");

        return inputHandler.getInt("");
    }

    public int librarianMenu(InputHandler inputHandler) {
        System.out.println("\nwhat would you like to do?");
        System.out.println("1. Add Book");
        System.out.println("2. Edit Personal Info");
        System.out.println("3. View and Approve Requests");
        System.out.println("4. View Activity Report");
        System.out.println("5. Back to Main Menu");

        return inputHandler.getInt("");
    }

    public int managerMenu(InputHandler inputHandler) {
        System.out.println("what would you like to do?");
        System.out.println("1. Add Librarian");
        System.out.println("2. View All Books");
        System.out.println("3. View Delayed Returns");
        System.out.println("4. View Librarian Activity Report");
        System.out.println("5. View Popular Books");
        System.out.println("6. Back to Main Menu");

        return inputHandler.getInt("");
    }
}
