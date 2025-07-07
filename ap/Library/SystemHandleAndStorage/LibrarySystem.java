package ap.Library.SystemHandleAndStorage;

import ap.Library.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class LibrarySystem {
    private Library library;
    private FileHandler fileHandler;
    private Menu menu;
    private InputHandler inputHandler;
    private Student currentStudent;
    private Librarian currentLibrarian;

    public LibrarySystem(Library library) throws SQLException, IOException {
        this.library = library;
        this.menu = new Menu();
        this.inputHandler = new InputHandler();
        this.fileHandler = new FileHandler();
    }

    public void run() {
        fileHandler.loadLibraryData(library);

        while (true) {
            int choice = menu.mainMenu(inputHandler);

            switch (choice) {
                case 1:
                    handleStudentLogin();
                    break;
                case 2:
                    handleStudentRegistration();
                    break;
                case 3:
                    handleLibrarianLogin();
                    break;
                case 4:
                    handleManagerLogin();
                    break;
                case 5:
                    fileHandler.saveLibraryData(library);
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid selection.");
            }
        }
    }
    private void handleStudentLogin() {
        int stdNumber = inputHandler.getInt("Enter student number: ");
        Student student = library.searchStudent(stdNumber);
        if (student != null && student.login(stdNumber)) {
            currentStudent = student;
            System.out.println("Welcome, " + currentStudent.getName());

            while (true) {
                int choice = menu.studentMenu(inputHandler);
                switch (choice) {
                    case 1:
                        library.printBookList();
                        break;
                    case 2:
                        searchBook();
                        break;
                    case 3:
                        int input = inputHandler.getInt("1. Borrow\n2. Return\n");
                        if (input == 1) {
                            loanRequest(LoanType.LOAN_REQUEST);
                        } else if (input == 2) {
                            loanRequest(LoanType.RETURN_REQUEST);
                        }
                        break;
                    case 4:
//                        currentStudent.listBorrowedBooks();
                        break;
                    case 5:
//                        listBorrowRecord();
                        break;
                    case 6:
                        currentStudent = null;
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } else {
            System.out.println("Student not found.");
        }
    }
    private void handleStudentRegistration() {
        System.out.println("Please enter your information:");
        String firstName = inputHandler.getLine("First Name: ");
        String lastName = inputHandler.getLine("Last Name: ");
        String major = inputHandler.getLine("Major: ");
        int stdNumber = 140301 + library.getStudents().size();

        Student newStudent = new Student(firstName, lastName, stdNumber, major);
        library.addStudent(newStudent);
        System.out.println("Registered successfully! Your student number is: " + stdNumber);
    }
    private void handleLibrarianLogin() {
        int id = inputHandler.getInt("Enter librarian ID: ");
        Librarian librarian = library.searchLibrarian(id);
        if (librarian != null && librarian.login(id)) {
                currentLibrarian = librarian;
                System.out.println("Welcome, " + currentLibrarian.getName());

                while (true) {
                    int choice = menu.librarianMenu(inputHandler);
                    switch (choice) {
                        case 1:
                            addNewBook();
                            break;
                        case 2:
                            editLibrarianInfo(currentLibrarian);
                            break;
                        case 3:
//                            handleLibrarianRequests();
                            break;
                        case 4:
//                        listLibrarianActivity();
                            break;
                        case 5:
                            currentLibrarian = null;
                            return;
                        default:
                            System.out.println("Invalid choice.");
                    }
                }

        } else {
            System.out.println("Librarian not found.");
        }
    }

    private void handleManagerLogin() {
        int id = inputHandler.getInt("Enter manager ID: ");
        Manager manager = library.getManager();
        if (manager != null && manager.login(id)) {
            System.out.println("Welcome, Manager!");

            while (true) {
                int choice = menu.managerMenu(inputHandler);
                switch (choice) {
                    case 1:
                        addNewLibrarian();
                        break;
                    case 2:
//                        library.printBookList();
                        break;
                    case 3:
//                        viewDelayedReturns(library);
                        break;
                    case 4:
//                        viewLibrarianReport(library);
                        break;
                    case 5:
//                        viewPopularBooks(library);
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } else {
            System.out.println("Invalid manager ID.");
        }
    }
    private void addNewLibrarian() {
        String firstName = inputHandler.getLine("First name: ");
        String lastName = inputHandler.getLine("Last name: ");
        int id = inputHandler.getInt("ID: ");

        library.addLibrarian(new Librarian(firstName, lastName, id));
        System.out.println("Librarian added successfully.");
    }
    private void addNewBook() {
        String title = inputHandler.getLine("Book title: ");
        String author = inputHandler.getLine("Author name: ");
        int year = inputHandler.getInt("Publish year: ");
        int pages = inputHandler.getInt("Page count: ");
        int code = inputHandler.getInt("Book code: ");

        Book book = new Book(title, author, year, pages, code);
        library.addBook(book);
        System.out.println("Book added successfully!");
    }
    private void editLibrarianInfo(Librarian librarian) {
        String firstName = inputHandler.getLine("Enter new first name: ");
        String lastName = inputHandler.getLine("Enter new last name: ");
        librarian.setName(firstName, lastName);
        System.out.println("Information updated.");
    }
    private void searchBook() {
        int choice = inputHandler.getInt("Search by:\n 1. Code \n 2. Title");

        if (choice == 1) {
            int code = inputHandler.getInt("Enter book code: ");
            Book book = library.searchBook(code);
            if (book != null) {
                System.out.println(book);
            } else {
                System.out.println("Book not found.");
            }
        } else if (choice == 2) {
            String title = inputHandler.getLine("Enter book title: ");
            List<Book> results = library.searchBooksByTitle(title);
            if (results.isEmpty()) {
                System.out.println("No books found.");
            } else {
                for (Book b : results) {
                    System.out.println(b);
                }
            }
        }
    }
    private void loanRequest(LoanType type) {
        int code = inputHandler.getInt("Enter book code: ");
        Book requestedBook = library.searchBook(code);

        if (requestedBook == null) {
            System.out.println("Book not found!");
            return;
        }

        boolean duplicateExists = library.getLoans().stream()
                                 .anyMatch(loan -> loan.getType().equals(type) &&
                                  loan.getStudent().equals(currentStudent) && loan.getBook().equals(requestedBook));
        if (duplicateExists) {
            System.out.println("You have submitted this request before.");
            return;
        }
        if (type == LoanType.LOAN_REQUEST) {
            boolean alreadyBorrowed = currentStudent.getLoanedBooks().stream()
                    .anyMatch(loan -> loan.getBook().equals(requestedBook));

            if (alreadyBorrowed) {
                System.out.println("You already borrowed this book.");
                return;
            }
        }
        else if (type == LoanType.RETURN_REQUEST) {
            boolean neverBorrowed = currentStudent.getLoanedBooks().stream()
                    .noneMatch(loan -> loan.getBook().equals(requestedBook));

            if (neverBorrowed) {
                System.out.println("You never borrowed this book.");
                return;
            }
        }
        Random random = new Random();

        library.addLoan(new Loan(requestedBook, this.currentStudent,
                library.getLibrarians().get(random.nextInt(library.getLibrarians().size())), type));
        System.out.println("Request added successfully.");
    }
}
