package ap.finalproject.Menu;

import ap.finalproject.Book;
import ap.finalproject.Loan;
import ap.finalproject.LoanStatus;
import ap.finalproject.ManageSystem.LibrarySystem;
import ap.finalproject.Student;

import java.util.List;

public class StudentMenu {
    private InputHandler inputHandler;
    private LibrarySystem librarySystem;
    private Student student;

    public StudentMenu(InputHandler inputHandler, LibrarySystem librarySystem, Student student) {
        this.inputHandler = inputHandler;
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

            int choice = inputHandler.getIntInput(1, 7);

            switch (choice) {
                case 1:
                    System.out.println("\n--- My Information ---");
                    System.out.println(student);
                    break;
                case 2:
                    editStudentInformation();
                    break;
                case 3:
                    loanRequest();
                    break;
                case 4:
                    librarySystem.displayAvailableBooks();
                    break;
                case 5:
                    bookSearchForStudent();
                    break;
                case 6:
                    viewStudentLoans();
                    break;
                case 7:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void editStudentInformation() {
        System.out.println("\n--- Edit My Information ---");
        System.out.println("Current Information:");
        System.out.println("Name: " + student.getName());
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Username: " + student.getUsername());

        System.out.print("Enter new name (leave blank if you don't want to change it): ");
        String newName = inputHandler.getStringInput();
        System.out.print("Enter new student ID (leave blank if you don't want to change it): ");
        String newStudentId = inputHandler.getStringInput();
        System.out.print("Enter new username (leave blank if you don't want to change it): ");
        String newUsername = inputHandler.getStringInput();
        System.out.print("Enter new password (leave blank if you don't want to change it): ");
        String newPassword = inputHandler.getStringInput();

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

    private void bookSearchForStudent() {
        System.out.println("\n--- Search for Books ---");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.println("3. Search by Publication Year");
        System.out.print("Please enter your choice: ");

        int searchType = inputHandler.getIntInput(1, 3);
        List<Book> foundBooks = null;

        switch (searchType) {
            case 1:
                System.out.print("Enter book title: ");
                String title = inputHandler.getStringInput();
                foundBooks = librarySystem.searchBooksByTitle(title);
                break;
            case 2:
                System.out.print("Enter author name: ");
                String author = inputHandler.getStringInput();
                foundBooks = librarySystem.searchBooksByAuthor(author);
                break;
            case 3:
                System.out.print("Enter publication year: ");
                int year = inputHandler.getIntInput(1000, 2025);
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

    private void loanRequest() {
        System.out.println("\n--- Borrow a Book ---");
        System.out.print("Enter the book code of the book you want to borrow: ");
        int bookCode = inputHandler.getIntInput(1, 99999);

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

    private void viewStudentLoans() {
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
}