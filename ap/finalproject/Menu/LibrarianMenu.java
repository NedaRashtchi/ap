package ap.finalproject.Menu;

import ap.finalproject.*;
import ap.finalproject.ManageSystem.LibrarySystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LibrarianMenu {
    private Scanner scanner;
    private LibrarySystem librarySystem;
    private Librarian librarian;

    public LibrarianMenu(Scanner scanner, LibrarySystem librarySystem, Librarian librarian) {
        this.scanner = scanner;
        this.librarySystem = librarySystem;
        this.librarian = librarian;
    }

    public void displayLibrarianMenu() {
        while (true) {
            System.out.println("\n=== Librarian Dashboard ===");
            System.out.println("1. View My Information");
            System.out.println("2. Change Password");
            System.out.println("3. Add New Book");
            System.out.println("4. View All Books");
            System.out.println("5. Edit Book Information");
            System.out.println("6. Approve Loan Requests");
            System.out.println("7. Return Book ");
            System.out.println("8. View Student Loan History");
            System.out.println("9. Activate/Deactivate Student");
            System.out.println("10. Logout");
            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 10);

            switch (choice) {
                case 1:
                    System.out.println("\n--- My Information ---");
                    System.out.println(librarian);
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
                    handleApproveLoanRequests();
                    break;
                case 7:
                    handleReturnBook();
                    break;
                case 8:
                    handleViewStudentLoanHistory();
                    break;
                case 9:
                    handleActivateDeactivateStudent();
                    break;
                case 10:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void handleActivateDeactivateStudent() {
        System.out.println("\n--- Activate/Deactivate Student ---");
        System.out.println("1. Activate Student");
        System.out.println("2. Deactivate Student");
        System.out.print("Please enter your choice: ");
        int actionChoice = getIntInput(1, 2);

        System.out.print("Enter student username: ");
        String username = scanner.nextLine();

        Student student = librarySystem.getStudentManager().getStudents().stream()
                .filter(s -> s.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Student found: " + student.getName());
        System.out.println("Current status: " + (student.isActive() ? "Active" : "Inactive"));
        System.out.println("Number of delayed returns: " + student.getDelayedReturns());

        if (actionChoice == 1) {
            System.out.print("Are you sure you want to activate this student? (yes/no): ");
        } else {
            System.out.print("Are you sure you want to deactivate this student? (yes/no): ");
        }
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            boolean success;
            if (actionChoice == 1) {
                success = librarySystem.activateStudent(username);
            } else {
                success = librarySystem.deactivateStudent(username);
            }
            if (success) {
                System.out.println("Student " + (actionChoice == 1 ? "activated" : "deactivated") + " successfully.");
                librarySystem.saveData();
            } else {
                System.out.println("Failed to " + (actionChoice == 1 ? "activate" : "deactivate") + " student.");
            }
        } else {
            System.out.println("Operation cancelled.");
        }
    }

    private void handleReturnBook() {
        System.out.println("\n--- Return a Book ---");
        System.out.println("1. Search by Student Name");
        System.out.println("2. Search by Book Title");
        System.out.println("3. Show All Borrowed Books");
        System.out.print("Please enter your choice: ");

        int choice = getIntInput(1, 3);
        List<Loan> loansToDisplay = new ArrayList<>();

        switch (choice) {
            case 1:
                System.out.print("Enter student name: ");
                String studentName = scanner.nextLine();
                loansToDisplay = librarySystem.getLoanManager().getLentBooks().stream()
                        .filter(loan -> loan.getStudent().getName().toLowerCase().contains(studentName.toLowerCase()))
                        .filter(loan -> loan.getStatus() == LoanStatus.BORROWED)
                        .collect(Collectors.toList());
                break;
            case 2:
                System.out.print("Enter book title: ");
                String bookTitle = scanner.nextLine();
                loansToDisplay = librarySystem.getLoanManager().getLentBooks().stream()
                        .filter(loan -> loan.getBook().getTitle().toLowerCase().contains(bookTitle.toLowerCase()))
                        .filter(loan -> loan.getStatus() == LoanStatus.BORROWED)
                        .collect(Collectors.toList());
                break;
            case 3:
                loansToDisplay = librarySystem.getLoanManager().getBorrowedLoans();
                break;
        }

        if (loansToDisplay.isEmpty()) {
            System.out.println("No borrowed books found.");
            return;
        }

        System.out.println("\n--- Borrowed Books ---");
        for (int i = 0; i < loansToDisplay.size(); i++) {
            Loan loan = loansToDisplay.get(i);
            System.out.println((i + 1) + ". " + loan.getStudent().getName() + " - " +
                    loan.getBook().getTitle() + " (Due: " + loan.getReturnDate() + ")");
        }

        System.out.print("Enter the number of the loan to return (0 to cancel): ");
        int loanChoice = getIntInput(0, loansToDisplay.size());

        if (loanChoice == 0) {
            return;
        }

        Loan selectedLoan = loansToDisplay.get(loanChoice - 1);
        System.out.print("Are you sure you want to return this book? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            boolean success = librarySystem.returnBook(selectedLoan, librarian);
            if (success) {
                System.out.println("Book returned successfully!");
            } else {
                System.out.println("Failed to return the book.");
            }
        } else {
            System.out.println("Return cancelled.");
        }
    }

    private void handleViewStudentLoanHistory() {
        System.out.println("\n--- View Student Loan History ---");
        System.out.print("Enter student username: ");
        String username = scanner.nextLine();

        Student student = librarySystem.getStudentManager().getStudents().stream()
                .filter(s -> s.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("\n--- Student Statistics ---");
        System.out.println("Student Name: " + student.getName());
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Total Loans: " + student.getTotalLoans());
        System.out.println("Pending Returns: " + student.getPendingReturns());
        System.out.println("Delayed Returns: " + student.getDelayedReturns());
        System.out.println("Total Delay Days: " + student.getTotalDelayDays());

        List<Loan> studentLoans = librarySystem.getLoanManager().getLoansByStudent(student);
        System.out.println("\n--- Loan History ---");
        if (studentLoans.isEmpty()) {
            System.out.println("No loan history found for this student.");
        } else {
            for (Loan loan : studentLoans) {
                System.out.println(loan);
            }
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
        if (librarySystem.changeLibrarianPassword(librarian.getUsername(), currentPassword, newPassword)) {
            System.out.println("Password changed successfully!");
            librarySystem.saveData();
        } else {
            System.out.println("Failed to change password.");
        }
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
        if (success) {
            librarian.addBooksAdded();
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
                        System.out.println("This book code already exist. Please choose a different code.");
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

    private void handleApproveLoanRequests() {
        List<Loan> requestedLoans = librarySystem.getLoanManager().getRequestedLoans();

        if (requestedLoans.isEmpty()) {
            System.out.println("There are no loan requests to approve.");
            return;
        }
        System.out.println("\n--- Loan Requests ---");
        for (int i = 0; i < requestedLoans.size(); i++) {
            Loan loan = requestedLoans.get(i);
            System.out.println((i+1) + ". " + loan.getStudent().getName() + " - " + loan.getBook().getTitle());
        }
        System.out.print("Enter the number of the loan to approve (0 to cancel): ");
        int choice = getIntInput(0, requestedLoans.size());

        if (choice == 0) {
            return;
        }
        boolean success = librarySystem.approveLoan(choice-1, librarian);
        if (success) {
            System.out.println("Loan approved successfully.");
            librarySystem.saveData();
        } else {
            System.out.println("Failed to approve the loan.");
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