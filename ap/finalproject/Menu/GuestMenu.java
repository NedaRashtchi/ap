package ap.finalproject.Menu;

import ap.finalproject.Book;
import ap.finalproject.Loan;
import ap.finalproject.ManageSystem.LibrarySystem;

import java.util.List;
import java.util.Scanner;

public class GuestMenu {
    private Scanner scanner;
    private LibrarySystem librarySystem;

    public GuestMenu(Scanner scanner, LibrarySystem librarySystem) {
        this.scanner = scanner;
        this.librarySystem = librarySystem;
    }

    public void displayGuestMenu() {
        while (true) {
            System.out.println("\n=== Guest Menu ===");
            System.out.println("1. View Available Books");
            System.out.println("2. Search Books by Title");
            System.out.println("3. View Registered Student Count");
            System.out.println("4. View Book Count in Library");
            System.out.println("5. View Recent Loans (Last Week)");
            System.out.println("6. Back to Main Menu");
            System.out.print("Please enter your choice: ");

            int choice = getIntInput(1, 6);

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
                    break;
                case 5:
                    handleRecentLoans();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void handleRecentLoans() {
        List<Loan> recentLoans = librarySystem.getLoansInLastWeek();
        int count = recentLoans.size();
        System.out.println("\nNumber of loans in the last week: " + count);

        if (count > 0) {
            System.out.print("Do you want to view the list of recent loans? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                System.out.println("\n--- Recent Loans (Last Week) ---");
                for (Loan loan : recentLoans) {
                    System.out.println(loan.getBook());
                }
            }
        } else {
            System.out.println("No loans found in the last week.");
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