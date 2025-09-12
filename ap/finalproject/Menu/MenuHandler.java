package ap.finalproject.Menu;

import ap.finalproject.Librarian;
import ap.finalproject.ManageSystem.LibrarySystem;
import ap.finalproject.Person;
import ap.finalproject.Student;

public class MenuHandler {
    private InputHandler inputHandler;
    private LibrarySystem librarySystem;
    private Person currentUser;

    public MenuHandler(LibrarySystem librarySystem) {
        this.inputHandler = new InputHandler();
        this.librarySystem = librarySystem;
        this.currentUser = null;
    }

    public void displayMainMenu() {
        while (true) {
            System.out.println("\n=== University Library Management System ===");
            System.out.println("1. Student Registration");
            System.out.println("2. Student Login");
            System.out.println("3. Librarian Login");
            System.out.println("4. Manager Login");
            System.out.println("5. Enter as Guest");
            System.out.println("6. Exit");
            System.out.print("Please enter your choice: ");

            int choice = inputHandler.getIntInput(1, 6);

            switch (choice) {
                case 1:
                    handleStudentRegistration();
                    break;
                case 2:
                    handleStudentLogin();
                    break;
                case 3:
                    handleLibrarianLogin();
                    break;
                case 4:
                    handleManagerLogin();
                    break;
                case 5:
                    GuestMenu guestMenu = new GuestMenu(inputHandler, librarySystem);
                    guestMenu.displayGuestMenu();
                    break;
                case 6:
                    librarySystem.exit();
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
            System.out.println("___________________________");
        }
    }

    private void handleStudentRegistration() {
        System.out.println("\n--- New Student Registration ---");
        System.out.print("Student name: ");
        String name = inputHandler.getStringInput();
        System.out.print("Student ID: ");
        String studentId = inputHandler.getStringInput();
        System.out.print("Username: ");
        String username = inputHandler.getStringInput();
        System.out.print("Password: ");
        String password = inputHandler.getStringInput();
        librarySystem.registerStudent(name, studentId, username, password);
    }

    private void handleStudentLogin() {
        System.out.println("\n--- Student Login ---");
        System.out.print("Username: ");
        String username = inputHandler.getStringInput();
        System.out.print("Password: ");
        String password = inputHandler.getStringInput();
        currentUser = librarySystem.authenticateStudent(username, password);
        if (currentUser != null) {
            System.out.println("Login successful! Welcome, " + currentUser.getName());
            StudentMenu studentMenu = new StudentMenu(inputHandler, librarySystem, (Student) currentUser);
            studentMenu.displayStudentMenu();
            currentUser = null;
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private void handleLibrarianLogin() {
        System.out.println("\n--- Librarian Login ---");
        System.out.print("Username: ");
        String username = inputHandler.getStringInput();
        System.out.print("Password: ");
        String password = inputHandler.getStringInput();
        currentUser = librarySystem.authenticateLibrarian(username, password);
        if (currentUser != null) {
            System.out.println("Login successful! Welcome, " + currentUser.getName());
            LibrarianMenu librarianMenu = new LibrarianMenu(inputHandler, librarySystem, (Librarian) currentUser);
            librarianMenu.displayLibrarianMenu();
            currentUser = null;
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private void handleManagerLogin() {
        System.out.println("\n--- Manager Login ---");
        System.out.print("Username: ");
        String username = inputHandler.getStringInput();
        System.out.print("Password: ");
        String password = inputHandler.getStringInput();
        if (librarySystem.authenticateManager(username, password)) {
            System.out.println("Login successful! Welcome, Manager");
            ManagerMenu managerMenu = new ManagerMenu(inputHandler, librarySystem);
            managerMenu.displayManagerMenu();
        }
    }
}