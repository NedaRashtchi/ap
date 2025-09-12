package ap.finalproject;

import java.util.List;
import java.util.Scanner;

public class ManagerMenu {
    private Scanner scanner;
    private LibrarySystem librarySystem;

    public ManagerMenu(Scanner scanner, LibrarySystem librarySystem) {
        this.scanner = scanner;
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

            int choice = getIntInput(1, 5);

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

    private void handleViewStudentLoanHistory() {
        System.out.println("\n--- View Student Loan History ---");
        System.out.print("Enter student username: ");
        String username = scanner.nextLine();

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