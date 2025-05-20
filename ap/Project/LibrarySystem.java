package ap.Project;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class LibrarySystem {
    private Library library;
    private FileHandler fileHandler;
    private Menu menu;
    private InputHandler inputHandler;
    private Student currentStudent;
    private Librarian currentLibrarian;
//    private Manager currentManager;

    public LibrarySystem(Library library) {
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
        if (student != null) {
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
                            bookRequest(RequestType.BORROW);
                        } else if (input == 2) {
                            bookRequest(RequestType.RETURN);
                        }
                        break;
                    case 4:
                        currentStudent.listBorrowedBooks();
                        break;
                    case 5:
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
        int index = library.searchLibrarian(id);
        if (index != -1) {
            currentLibrarian = library.getLibrarians().get(index);
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
                        handleLibrarianRequests(currentLibrarian);
                        break;
                    case 4:
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
        if (id == library.getManager().getId()) {
            System.out.println("Welcome, Manager!");

            while (true) {
                int choice = menu.managerMenu(inputHandler);
                switch (choice) {
                    case 1:
                        addNewLibrarian();
                        break;
                    case 2:
                        library.printBookList();
                        break;
                    case 3:
                        viewDelayedReturns(library);
                        break;
                    case 4:
                        viewLibrarianReport(library);
                        break;
                    case 5:
                        viewPopularBooks(library);
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

    private void addNewLibrarian() {
        String firstName = inputHandler.getLine("First name: ");
        String lastName = inputHandler.getLine("Last name: ");
        int id = inputHandler.getInt("ID: ");

        library.addLibrarian(new Librarian(firstName, lastName, id, 0, 0));
        System.out.println("Librarian added successfully.");
    }

    private void viewDelayedReturns(Library library) {
        List<Borrow> delayedList = library.getDelayedReturns();
        if (delayedList.isEmpty()) {
            System.out.println("No delayed returns found.");
            return;
        }

        System.out.println("\nDelayed Return Books:");
        for (Borrow borrow : delayedList) {
            System.out.println(borrow.toString());
            System.out.println("Days Delayed: " + ChronoUnit.DAYS.between(borrow.getReturnDate(), LocalDate.now()));
        }
    }

    private void viewLibrarianReport(Library library) {
        System.out.println("\nLibrarian Activity  Report:");
        for (Librarian librarian : library.getLibrarians()) {
            System.out.println(librarian.toString());
        }
    }

    private void viewPopularBooks(Library library) {
        ArrayList<int[]> popularBooks = new ArrayList<>();
        int borrowCount;

        for (Book book : library.getBooks().values()) {
            borrowCount = 0;

            for (Borrow borrow : library.getBorrowRecords()) {
                if (borrow.getBook().equals(book) && borrow.getBorrowDate().isAfter(LocalDate.now().minusYears(1))) {
                    borrowCount++;
                }
            }

            if (borrowCount > 0) {
                int[] b = {book.getBookCode(), borrowCount};
                popularBooks.add(b);
            }
        }
        if (popularBooks.isEmpty()) {
            System.out.println("No popular books this year.");
            return;
        }
        for (int i = 0; i < popularBooks.size() - 1; i++) {
            for (int j = 0; j < popularBooks.size() - i - 1; j++) {
                if (popularBooks.get(j)[1] < popularBooks.get(j + 1)[1]) {

                    int[] temp = popularBooks.get(j);
                    popularBooks.set(j, popularBooks.get(j + 1));
                    popularBooks.set(j + 1, temp);
                }
            }
        }

        System.out.println("\nPopular Books in the Last Year:");
        for (int i = 0; i < Math.min(10, popularBooks.size()); i++) {
            int bookCode = popularBooks.get(i)[0];
            int count = popularBooks.get(i)[1];
            Book book = library.getBooks().get(bookCode);

            if (book != null) {
                System.out.printf("%d. %s borrowed: %d time\n", i + 1, book.getTitle(), count);
            }
        }
    }

    private void searchBook() {
        System.out.println("Search by:\n 1. Code \n 2. Title");
        int choice = inputHandler.getInt("");

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

    private void bookRequest(RequestType type) {
        int code = inputHandler.getInt("Enter book code: ");
        boolean found = false;

        for (Book borrowedBook : currentStudent.getBorrowedBooks()) {
            if (type == RequestType.BORROW && borrowedBook.getBookCode() == code) {
                System.out.println("You already borrowed this book.");
                return;
            } else if (type == RequestType.RETURN && borrowedBook.getBookCode() == code) {
                found = true;
            }
        }
        if (!found && type == RequestType.RETURN) {
            System.out.println("You never borrowed this book.");
            return;
        }

        Book b = library.searchBook(code);
        Random random = new Random();

        library.addRequest(new Request(b, this.currentStudent,
                library.getLibrarians().get(random.nextInt(library.getLibrarians().size()))
                , type));
        System.out.println("Request added successfully.");
    }

    private void handleLibrarianRequests(Librarian librarian) {
        List<Request> requests = library.getRequestsByLibrarian(librarian.getId());
        if (requests.isEmpty()) {
            System.out.println("There is no request.");
            return;
        }
        System.out.println("\nYour requests:");
        for (int i = 0; i < requests.size(); i++) {
            System.out.println((i + 1) + ". " + requests.get(i));
        }
        int choice = inputHandler.getInt("Enter request number to approve: ");
        if (choice < 1 || choice > requests.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Request selectedRequest = requests.get(choice - 1);

        if (selectedRequest.getType() == RequestType.BORROW) {

            library.addBorrow(new Borrow(
                    selectedRequest.getBook(), selectedRequest.getStudent(),
                    LocalDate.now(), selectedRequest.getLibrarian()));
            selectedRequest.getStudent().addBorrowedBook(selectedRequest.getBook());
            currentLibrarian.addBorrowCount();

            library.addBorrowRecord(new Borrow(
                    selectedRequest.getBook(), selectedRequest.getStudent(),
                    LocalDate.now(), selectedRequest.getLibrarian()));
            selectedRequest.getStudent().addBorrowedBook(selectedRequest.getBook());

            System.out.println("Borrow request approved successfully.");

        } else if (selectedRequest.getType() == RequestType.RETURN) {
            Book book = selectedRequest.getBook();
            Student student = selectedRequest.getStudent();
            student.removeBorrowedBook(book);

            Borrow foundBorrow = null;
            for (Borrow b : library.getBorrows()) {
                if (b.getBook().equals(book) && b.getStudent().equals(student)) {
                    foundBorrow = b;
                    break;
                }
            }
            if (foundBorrow != null) {

                if (LocalDate.now().isAfter(foundBorrow.getReturnDate())) {
                    library.addDelayedReturn(foundBorrow);
                    System.out.println("Delayed Return");
                }
                foundBorrow.setReturner(librarian);

                library.getBorrows().remove(foundBorrow);
            }

            currentLibrarian.addReturnCount();
            System.out.println("Return request approved successfully.");
        }

        library.getRequests().remove(selectedRequest);
    }
}