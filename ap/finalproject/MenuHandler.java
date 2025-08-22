package ap.finalproject;

// MenuHandler.java
import java.util.List;
import java.util.Scanner;

public class MenuHandler {
    private Scanner scanner;
    private LibrarySystem librarySystem;
    private Person currentUser;

    public MenuHandler(LibrarySystem librarySystem) {
        this.scanner = new Scanner(System.in);
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

            int choice = getIntInput(1, 6);

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
                    displayGuestMenu();
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

    private void displayGuestMenu() {
        while (true) {
            System.out.println("\n=== Guest Menu ===");
            System.out.println("1. View Available Books");
            System.out.println("2. Search Books by Title");
            System.out.println("3. View Registered Student Count");
            System.out.println("4. View Book Count in Library");
            System.out.println("5. Back to Main Menu");
            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 5);

            switch (choice) {
                case 1:
                    librarySystem.displayAvailableBooks();
                    break;
                case 2:
                    handleBookSearch();
                    break;
                case 3:
                    displayStudentCount();
                    break;
                case 4:
                    displayBookCount();
                case 5:
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void handleBookSearch() {
        System.out.print("\nEnter book title to search: ");
        String title = scanner.nextLine();
        List<Book> foundBooks = librarySystem.searchBooksByTitle(title);

        if (foundBooks.isEmpty()) {
            System.out.println("No books found with that title.");
        } else {
            System.out.println("\n--- Search Results ---");
            foundBooks.forEach(System.out::println);
        }
    }

    private void displayStudentCount() {
        int studentCount = librarySystem.getStudentCount();
        System.out.println("\nTotal registered students: " + studentCount);
    }

    private void displayBookCount() {
        int bookCount = librarySystem.getBookCount();
        System.out.println("\nTotal registered books: " + bookCount);
    }

    private void handleStudentRegistration() {
        System.out.println("\n--- New Student Registration ---");
        System.out.print("Student name: ");
        String name = scanner.nextLine();
        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        librarySystem.registerStudent(name, studentId, username, password);
    }

    private void handleStudentLogin() {
        System.out.println("\n--- Student Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        currentUser = librarySystem.authenticateStudent(username, password);
        if (currentUser != null) {
            System.out.println("Login successful! Welcome, " + currentUser.getName());
            displayLoggedInStudentMenu();
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private void handleLibrarianLogin() {
        System.out.println("\n--- Librarian Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        currentUser = librarySystem.authenticateLibrarian(username, password);
        if (currentUser != null) {
            System.out.println("Login successful! Welcome, " + currentUser.getName());
            displayLoggedInLibrarianMenu();
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private void handleManagerLogin() {
        System.out.println("\n--- Manager Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (librarySystem.authenticateManager(username, password)) {
            System.out.println("Login successful! Welcome, Manager");
            displayManagerMenu();
        }
    }

    private void displayLoggedInStudentMenu() {
        while (currentUser != null) {
            System.out.println("\n=== Student Dashboard ===");
            System.out.println("1. View My Information");
            System.out.println("2. Edit My Information");
            System.out.println("3. Borrow a Book");
            System.out.println("4. Return a Book");
            System.out.println("5. View Available Books");
            System.out.println("6. Search for Books");
            System.out.println("7. Logout");
            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 7);
            Student student = (Student) currentUser;

            switch (choice) {
                case 1:
                    System.out.println("\n--- My Information ---");
                    System.out.println(student);
                    break;
                case 2:
                    librarySystem.editStudentInformation(student);
                    break;
                case 3:
                    handleLoanRequest(student);
                    break;
                case 4:
                    librarySystem.returnBook(student);
                    break;
                case 5:
                    librarySystem.displayAvailableBooks();
                    break;
                case 6:
                    handleBookSearchForStudent();
                    break;
                case 7:
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void handleBookSearchForStudent() {
        System.out.println("\n--- Search for Books ---");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.println("3. Search by Publication Year");
        System.out.print("Please enter your choice: ");

        int searchType = getIntInput(1, 3);
        List<Book> foundBooks = null;

        switch (searchType) {
            case 1:
                System.out.print("Enter book title: ");
                String title = scanner.nextLine();
                foundBooks = librarySystem.searchBooksByTitle(title);
                break;
            case 2:
                System.out.print("Enter author name: ");
                String author = scanner.nextLine();
                foundBooks = librarySystem.searchBooksByAuthor(author);
                break;
            case 3:
                System.out.print("Enter publication year: ");
                int year = getIntInput(1000, 2025);
                foundBooks = librarySystem.searchBooksByPublicationYear(year);
                break;
        }

        if (foundBooks.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("\n--- Search Results ---");
            foundBooks.forEach(System.out::println);
        }
    }

    private void handleLoanRequest(Student student) {
        System.out.println("\n--- Borrow a Book ---");
        System.out.print("Enter the book code of the book you want to borrow: ");
        int bookCode = getIntInput(1, 99999);

        Book book = librarySystem.searchBookByBookCode(bookCode);
        if (book == null) {
            System.out.println("No book found with that code.");
            return;
        }

        if (!book.getStatus().equals("Available")) {
            System.out.println("This book is not available for borrowing.");
            return;
        }

        boolean success = librarySystem.requestLoan(student, book);
        if (success) {
            System.out.println("Loan request submitted successfully.");
        } else {
            System.out.println("Failed to submit loan request.");
        }
    }

    private void displayLoggedInLibrarianMenu() {
        while (currentUser != null) {
            System.out.println("\n=== Librarian Dashboard ===");
            System.out.println("1. View My Information");
            System.out.println("2. Change Password");
            System.out.println("3. Add New Book");
            System.out.println("4. View All Books");
            System.out.println("5. Edit Book Information");
            System.out.println("6. Logout");
            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 6);

            switch (choice) {
                case 1:
                    System.out.println("\n--- My Information ---");
                    System.out.println(currentUser);
                    break;
                case 2:
                    handleChangeLibrarianPassword();
                    break;
                case 3:
                    handleAddBook();
                    break;
                case 4:
                    librarySystem.displayAvailableBooks();
                    break;
                case 5:
                    handleEditBook();
                    break;
                case 6:
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void displayManagerMenu() {
        while (true) {
            System.out.println("\n=== Manager Dashboard ===");
            System.out.println("1. Add New Librarian");
            System.out.println("2. View Librarian Performance Report");
            System.out.println("3. Logout");
            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 3);

            switch (choice) {
                case 1:
                    handleLibrarianRegistration();
                    break;
                case 2:
                    handleLibrarianPerformanceReport();
                    break;
                case 3:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void handleLibrarianPerformanceReport() {
        System.out.println("\n--- Librarian Performance Report ---");
        System.out.print("Enter librarian username: ");
        String username = scanner.nextLine();

        Librarian librarian = librarySystem.getLibrarianByUsername(username);
        if (librarian != null) {
            System.out.println("\n--- Performance Report for " + librarian.getName() + " ---");
            System.out.println("Total Books Added: " + librarian.getBooksAdded());
            System.out.println("Total Books Lent: " + librarian.getBooksLent());
            System.out.println("Total Books Returned: " + librarian.getBooksReturned());
        } else {
            System.out.println("Librarian with username '" + username + "' not found.");
        }
    }

    private void handleChangeLibrarianPassword() {
        System.out.println("\n--- Change Password ---");
        System.out.print("Current Password: ");
        String currentPassword = scanner.nextLine();

        System.out.print("New Password: ");
        String newPassword = scanner.nextLine();

        System.out.print("Confirm New Password: ");
        String confirmPassword = scanner.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("New passwords do not match!");
            return;
        }

        if (librarySystem.changeLibrarianPassword(currentUser.getUsername(), currentPassword, newPassword)) {
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Failed to change password.");
        }
    }

    private void handleLibrarianRegistration() {
        System.out.println("\n--- New Librarian Registration ---");
        System.out.print("Librarian name: ");
        String name = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        librarySystem.registerLibrarian(name, username, password);
    }

    private void handleAddBook() {
        System.out.println("\n--- Add New Book ---");
        System.out.print("Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();

        System.out.print("Publication Year: ");
        int year = getIntInput(1000, 2025);

        System.out.print("Page Count: ");
        int pages = getIntInput(1, 9999);

        System.out.print("Book Code: ");
        int code = getIntInput(1, 99999);

        boolean success = librarySystem.addBook(title, author, year, pages, code);

        while (!success) {
            System.out.print("Please enter a different book code: ");
            code = getIntInput(1, 99999);
            success = librarySystem.addBook(title, author, year, pages, code);
        }
        if (success && currentUser instanceof Librarian) {
            ((Librarian) currentUser).addBooksAdded();
            librarySystem.saveData();
        }
    }

    private void handleEditBook() {
        System.out.println("\n--- Edit Book Information ---");
        System.out.println("How do you want to search for the book?");
        System.out.println("1. By Title");
        System.out.println("2. By Book Code");
        System.out.print("Enter your choice: ");
        int searchChoice = getIntInput(1, 2);

        Book bookToEdit = null;
        if (searchChoice == 1) {
            System.out.print("Enter book title: ");
            String title = scanner.nextLine();
            List<Book> books = librarySystem.searchBooksByTitle(title);
            if (books.isEmpty()) {
                System.out.println("No books found with that title.");
                return;
            } else if (books.size() == 1) {
                bookToEdit = books.get(0);
            } else {
                System.out.println("Multiple books found. Please select one by code:");
                for (Book book : books) {
                    System.out.println(book);
                }
                System.out.print("Enter the book code of the book you want to edit: ");
                int code = getIntInput(1, 99999);
                bookToEdit = librarySystem.searchBookByBookCode(code);
                if (bookToEdit == null) {
                    System.out.println("Invalid book code.");
                    return;
                }
            }
        } else {
            System.out.print("Enter book code: ");
            int code = getIntInput(1, 99999);
            bookToEdit = librarySystem.searchBookByBookCode(code);
            if (bookToEdit == null) {
                System.out.println("No book found with that code.");
                return;
            }
        }
        editBookDetails(bookToEdit);
    }

    private void editBookDetails(Book book) {
        while (true) {
            System.out.println("\nCurrent book information:");
            System.out.println(book);
            System.out.println("\nChoose what you want to edit?");
            System.out.println("1. Title");
            System.out.println("2. Author");
            System.out.println("3. Publication Year");
            System.out.println("4. Page Count");
            System.out.println("5. Book Code");
            System.out.println("6. Finish Editing");
            System.out.print("Enter your choice: ");

            int choice = getIntInput(1, 6);

            switch (choice) {
                case 1:
                    System.out.print("Enter new title: ");
                    String newTitle = scanner.nextLine();
                    book.setTitle(newTitle);
                    System.out.println("Title updated.");
                    break;
                case 2:
                    System.out.print("Enter new author: ");
                    String newAuthor = scanner.nextLine();
                    book.setAuthor(newAuthor);
                    System.out.println("Author updated.");
                    break;
                case 3:
                    System.out.print("Enter new publication year: ");
                    int newYear = getIntInput(1000, 2025);
                    book.setPublicationYear(newYear);
                    System.out.println("Publication year updated.");
                    break;
                case 4:
                    System.out.print("Enter new page count: ");
                    int newPages = getIntInput(1, 9999);
                    book.setPageCount(newPages);
                    System.out.println("Page count updated.");
                    break;
                case 5:
                    System.out.print("Enter new book code: ");
                    int newCode = getIntInput(1, 99999);
                    if (librarySystem.isBookCodeTaken(newCode, book)) {
                        System.out.println("This book code already taken by exist. Please choose a different code.");
                    } else {
                        book.setBookCode(newCode);
                        System.out.println("Book code updated.");
                    }
                    break;
                case 6:
                    librarySystem.saveData();
                    System.out.println("Changes saved.");
                    return;
            }
        }
    }

    private int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.printf("Please enter a number between %d and %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}