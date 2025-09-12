package ap.finalproject;

import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    private Scanner scanner;
    private LibrarySystem librarySystem;
    private Student student;

    public StudentMenu(Scanner scanner, LibrarySystem librarySystem, Student student) {
        this.scanner = scanner;
        this.librarySystem = librarySystem;
        this.student = student;
    }

    public void displayStudentMenu() {
        while (true) {
            System.out.println("\n=== Student Dashboard ===");
            System.out.println("1. View My Information");
            System.out.println("2. Edit My Information");
            System.out.println("3. Borrow a Book");
            System.out.println("4. View Available Books");
            System.out.println("5. Search for Books");
            System.out.println("6. View My Loans");
            System.out.println("7. Logout");
            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 7);

            switch (choice) {
                case 1:
                    System.out.println("\n--- My Information ---");
                    System.out.println(student);
                    break;
                case 2:
                    handleEditStudentInformation();
                    break;
                case 3:
                    handleLoanRequest();
                    break;
                case 4:
                    librarySystem.displayAvailableBooks();
                    break;
                case 5:
                    handleBookSearchForStudent();
                    break;
                case 6:
                    handleViewStudentLoans();
                    break;
                case 7:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void handleEditStudentInformation() {
        System.out.println("\n--- Edit My Information ---");
        System.out.println("Current Information:");
        System.out.println("Name: " + student.getName());
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Username: " + student.getUsername());

        System.out.print("Enter new name (leave blank if you don't want to change it): ");
        String newName = scanner.nextLine();
        System.out.print("Enter new student ID (leave blank if you don't want to change it): ");
        String newStudentId = scanner.nextLine();
        System.out.print("Enter new username (leave blank if you don't want to change it): ");
        String newUsername = scanner.nextLine();
        System.out.print("Enter new password (leave blank if you don't want to change it): ");
        String newPassword = scanner.nextLine();

        if (newName.isEmpty()) newName = student.getName();
        if (newStudentId.isEmpty()) newStudentId = student.getStudentId();
        if (newUsername.isEmpty()) newUsername = student.getUsername();
        if (newPassword.isEmpty()) newPassword = student.getPassword();

        boolean success = librarySystem.updateStudent(student, newName, newStudentId, newUsername, newPassword);
        if (success) {
            System.out.println("Information updated successfully.");
        } else {
            System.out.println("Failed to update information. Username might be taken.");
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

    private void handleLoanRequest() {
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

    private void handleViewStudentLoans() {
        List<Loan> studentLoans = librarySystem.getLoanManager().getLoansByStudent(student);
        if (studentLoans.isEmpty()) {
            System.out.println("You have no loans.");
            return;
        }
        System.out.println("\n--- My Loans ---");
        for (Loan loan : studentLoans) {
            String loanInfo = loan.toString();
            if (loan.getStatus() == LoanStatus.BORROWED && loan.getReturnDate() != null &&
                    java.time.LocalDate.now().isAfter(loan.getReturnDate())) {
                loanInfo += " (delayed)";
            }
            System.out.println(loanInfo);
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