package ap.finalproject.Menu;

import ap.finalproject.Librarian;
import ap.finalproject.Loan;
import ap.finalproject.ManageSystem.LibrarySystem;
import ap.finalproject.Student;

import java.util.List;

public class ManagerMenu {
    private InputHandler inputHandler;
    private LibrarySystem librarySystem;

    public ManagerMenu(InputHandler inputHandler, LibrarySystem librarySystem) {
        this.inputHandler = inputHandler;
        this.librarySystem = librarySystem;
    }

    public void displayManagerMenu() {
        while (true) {
            System.out.println("\n=== Manager Dashboard ===");
            System.out.println("1. Add New Librarian");
            System.out.println("2. View Librarian Performance Report");
            System.out.println("3. View Student Loan History");
            System.out.println("4. View Top 10 Students with Most Delays");
            System.out.println("5. Logout");
            System.out.print("Please enter your choice: ");

            int choice = inputHandler.getIntInput(1, 5);

            switch (choice) {
                case 1:
                    handleLibrarianRegistration();
                    break;
                case 2:
                    handleLibrarianPerformanceReport();
                    break;
                case 3:
                    handleViewStudentLoanHistory();
                    break;
                case 4:
                    handleTop10DelayedStudents();
                    break;
                case 5:
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
        String username = inputHandler.getStringInput();

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

    private void handleLibrarianRegistration() {
        System.out.println("\n--- New Librarian Registration ---");
        System.out.print("Librarian name: ");
        String name = inputHandler.getStringInput();
        System.out.print("Username: ");
        String username = inputHandler.getStringInput();
        System.out.print("Password: ");
        String password = inputHandler.getStringInput();
        librarySystem.registerLibrarian(name, username, password);
    }

    private void handleViewStudentLoanHistory() {
        System.out.println("\n--- View Student Loan History ---");
        System.out.print("Enter student username: ");
        String username = inputHandler.getStringInput();

        Student student = librarySystem.getStudentManager().getStudents().stream()
                .filter(s -> s.getUsername().equals(username))
                .findFirst().orElse(null);
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

    private void handleTop10DelayedStudents() {
        List<Student> topDelayedStudents = librarySystem.getTop10StudentsWithMostDelays();
        System.out.println("\n--- Top 10 Students with Most Delays ---");
        if (topDelayedStudents.isEmpty()) {
            System.out.println("No students with delays found.");
        } else {
            for (Student student : topDelayedStudents) {
                int delayedReturns = student.getDelayedReturns();
                int totalDelayDays = student.getTotalDelayDays();
                double averageDelayDays = delayedReturns > 0 ? (double) totalDelayDays / delayedReturns : 0;
                System.out.printf("Name: %s | Delayed Returns: %d | Average Delay Days: %.2f%n",
                        student.getName(), delayedReturns, averageDelayDays);
            }
        }
    }
}