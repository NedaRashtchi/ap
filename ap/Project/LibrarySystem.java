package ap.Project;

import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class LibrarySystem {
    private Library library;
    private Scanner scanner;
    private Menu menu;
    private InputHandler inputHandler;
    private Student currentStudent;
    private Librarian currentLibrarian;
    private Manager currentManager;

    public LibrarySystem(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
        this.menu = new Menu();
        this.inputHandler = new InputHandler();
    }

    public void run() {
        loadLibraryData();

        while (true) {
            int choice = menu.mainMenu(scanner);

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
                    saveLibraryData();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid selection.");
            }
        }
    }

    private void handleStudentLogin() {
        int stdNumber = inputHandler.getInt("Enter student number: ", scanner);
        Student student = library.searchStudent(stdNumber);
        if (student != null) {
            currentStudent = student;
            System.out.println("Welcome, " + currentStudent.getName());

            while (true) {
                int choice = menu.studentMenu(scanner);
                switch (choice) {
                    case 1:
                        library.printBookList();
                        break;
                    case 2:
                        searchBook();
                        break;
                    case 3:
                        int input = inputHandler.getInt("1. Borrow\n2. Return\n", scanner);
                        if(input == 1) {
                            bookRequest(RequestType.BORROW);
                        }else if(input == 2) {
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
        String firstName = inputHandler.getLine("First Name: ", scanner);
        String lastName = inputHandler.getLine("Last Name: ", scanner);
        String major = inputHandler.getLine("Major: ", scanner);
        int stdNumber = 140301 + library.getStudents().size();

        Student newStudent = new Student(firstName, lastName, stdNumber, major);
        library.addStudent(newStudent);
        System.out.println("Registered successfully! Your student number is: " + stdNumber);
    }

    private void handleLibrarianLogin() {
        int id = inputHandler.getInt("Enter librarian ID: ", scanner);
        int index = library.searchLibrarian(id);
        if ( index != -1) {
            currentLibrarian = library.getLibrarians().get(index);
            System.out.println("Welcome, " + currentLibrarian.getName());

            while (true) {
                int choice = menu.librarianMenu(scanner);
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
        int id = inputHandler.getInt("Enter manager ID: ", scanner);
        if (id == library.getManager().getId()) {
            System.out.println("Welcome, Manager!");

            while (true) {
                int choice = menu.managerMenu(scanner);
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
        String title = inputHandler.getLine("Book title: ", scanner);
        String author = inputHandler.getLine("Author name: ", scanner);
        int year = inputHandler.getInt("Publish year: ", scanner);
        int pages = inputHandler.getInt("Page count: ", scanner);
        int code = inputHandler.getInt("Book code: ", scanner);

        Book book = new Book(title, author, year, pages, code);
        library.addBook(book);
        System.out.println("Book added successfully!");
    }

    private void editLibrarianInfo(Librarian librarian) {
        String firstName = inputHandler.getLine("Enter new first name: ", scanner);
        String lastName = inputHandler.getLine("Enter new last name: ", scanner);
        librarian.setName(firstName, lastName);
        System.out.println("Information updated.");
    }

    private void addNewLibrarian() {
        String firstName = inputHandler.getLine("First name: ", scanner);
        String lastName = inputHandler.getLine("Last name: ", scanner);
        int id = inputHandler.getInt("ID: ", scanner);

        library.addLibrarian(new Librarian(firstName, lastName, id));
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

    private void searchBook() {
        System.out.println("Search by:\n 1. Code \n 2. Title");
        int choice = inputHandler.getInt("", scanner);
        scanner.nextLine();

        if (choice == 1) {
            int code = inputHandler.getInt("Enter book code: ", scanner);
            Book book = library.searchBook(code);
            if (book != null) {
                System.out.println(book);
            } else {
                System.out.println("Book not found.");
            }
        } else if (choice == 2) {
            String title = inputHandler.getLine("Enter book title: ", scanner);
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
        int code = inputHandler.getInt("Enter book code: ", scanner);
        Book b = library.searchBook(code);
        Random random = new Random();

        library.addRequest(new Request(b , this.currentStudent ,
                        library.getLibrarians().get(random.nextInt(2)) //library.getLibrarians().size()+1
                        ,type));
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
        int choice = inputHandler.getInt("Enter request number to approve: ", scanner);
        if (choice < 1 || choice > requests.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Request selectedRequest = requests.get(choice - 1);

        if (selectedRequest.getType() == RequestType.BORROW) {

            library.addBorrow(new Borrow(
                    selectedRequest.getBook(),
                    selectedRequest.getStudent(),
                    LocalDate.now(),
                    selectedRequest.getLibrarian()));
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

            System.out.println("Return request approved successfully.");
        }

        library.getRequests().remove(selectedRequest);
    }

    private void saveLibraryData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("library_data.txt"))) {

            writer.println("Books:");
            for (Book book : library.getBooks().values()) {
                writer.println(book.getTitle() + "," +
                        book.getAuthor() + "," +
                        book.getPublicationYear() + "," +
                        book.getPageCount() + "," +
                        book.getBookCode());
            }

            writer.println("Students:");
            for (Student student : library.getStudents().values()) {
                StringBuilder borrowedCodes = new StringBuilder();

                for (int i = 0; i < student.getBorrowedBooks().size(); i++) {
                    Book book = student.getBorrowedBooks().get(i);
                    borrowedCodes.append(book.getBookCode());

                    if (i < student.getBorrowedBooks().size() - 1) {
                        borrowedCodes.append(":");
                    }
                }

                writer.println(student.getStdNumber() + "," +
                        student.getName() + "," +
                        student.getMajor() + "," +
                        student.getRegisterDate() + "," +
                        borrowedCodes);
            }


            writer.println("Librarians:");
            for (Librarian librarian : library.getLibrarians()) {
                writer.println(librarian.getId() + "," +
                        librarian.getName() + "," +
                        librarian.getRegisterDate());
            }

            writer.println("Manager:");
            Manager manager = library.getManager();
            writer.println(manager.getId() + "," +
                    manager.getName() + "," +
                    manager.getEducation());

            writer.println("Requests:");
            for (Request request : library.getRequests()) {
                int stdNumber = request.getStudent() != null ? request.getStudent().getStdNumber() : -1;
                int libId = request.getLibrarian() != null ? request.getLibrarian().getId() : -1;
                int bookCode = request.getBook() != null ? request.getBook().getBookCode() : -1;

                writer.println(stdNumber + "," + libId + "," + bookCode + "," + request.getType());
            }

            writer.println("Borrows:");
            for (Borrow borrow : library.getBorrows()) {
                writer.println(borrow.getStudent().getStdNumber() + "," +
                        borrow.getBook().getBookCode() + "," +
                        borrow.getBorrowDate() + "," +
                        (borrow.getReturnDate() != null ? borrow.getReturnDate() : "") + "," +
                        borrow.getBorrower().getId() + "," +
                        (borrow.getReturner() != null ? borrow.getReturner().getId() : ""));
            }

            writer.println("DelayedReturns:");
            for (Borrow borrow : library.getDelayedReturns()) {
                writer.println(borrow.getStudent().getStdNumber() + "," +
                        borrow.getBook().getBookCode() + "," +
                        borrow.getBorrowDate() + "," +
                        borrow.getReturnDate() + "," +
                        borrow.getBorrower().getId() + "," +
                        borrow.getReturner().getId());
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadLibraryData() {
        try (Scanner fileScanner = new Scanner(new File("library_data.txt"))) {
            String section = "";

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                if (line.equals("Books:")) {
                    section = "Books";
                    continue;
                } else if (line.equals("Students:")) {
                    section = "Students";
                    continue;
                } else if (line.equals("Librarians:")) {
                    section = "Librarians";
                    continue;
                } else if (line.equals("Manager:")) {
                    section = "Manager";
                    continue;
                } else if (line.equals("Requests:")) {
                    section = "Requests";
                    continue;
                }else if (line.equals("Borrows:")) {
                    section = "Borrows";
                    continue;
                }else if (line.equals("DelayedReturns:")) {
                    section = "DelayedReturns";
                    continue;
                }

                if (line.trim().isEmpty()) continue;

                switch (section) {
                    case "Books":
                        String[] bookData = line.split(",");
                        Book book = new Book(
                                bookData[0],
                                bookData[1],
                                Integer.parseInt(bookData[2]),
                                Integer.parseInt(bookData[3]),
                                Integer.parseInt(bookData[4])
                        );
                        library.addBook(book);
                        break;

                    case "Students":
                        String[] stdData = line.split(",");
                        Student student = new Student(
                                stdData[1].split(" ")[0],
                                stdData[1].split(" ")[1],
                                Integer.parseInt(stdData[0]),
                                stdData[2]
                        );
                        student.setRegisterDate(LocalDate.parse(stdData[3]));

                        if (stdData.length > 4 && !stdData[4].isEmpty()) {
                            String[] codeStrings = stdData[4].split(":");
                            for (String codeStr : codeStrings) {
                                int bookCode = Integer.parseInt(codeStr);
                                Book b = library.searchBook(bookCode);
                                if (b != null) {
                                    student.addBorrowedBook(b);
                                }
                            }
                        }

                        library.addStudent(student);
                        break;

                    case "Librarians":
                        String[] libData = line.split(",");
                        Librarian librarian = new Librarian(
                                libData[1].split(" ")[0],
                                libData[1].split(" ")[1],
                                Integer.parseInt(libData[0])
                        );
                        librarian.setRegisterDate(LocalDate.parse(libData[2]));
                        library.addLibrarian(librarian);
                        break;

                    case "Manager":
                        String[] mgrData = line.split(",");
                        Manager manager = new Manager(
                                mgrData[1].split(" ")[0],
                                mgrData[1].split(" ")[1],
                                Education.valueOf(mgrData[2]),
                                Integer.parseInt(mgrData[0])
                        );
                        library.setManager(manager);
                        break;
                    case "Requests":
                        String[] reqData = line.split(",");
                        int stdNumber = Integer.parseInt(reqData[0]);
                        int libId = Integer.parseInt(reqData[1]);
                        int bookCode = Integer.parseInt(reqData[2]);
                        RequestType type = RequestType.valueOf(reqData[3]);

                        Book b = library.searchBook(bookCode);
                        Student s = library.getStudents().get(stdNumber);
                        Librarian l = null;
                        for (Librarian lib : library.getLibrarians()) {
                            if (lib.getId() == libId) {
                                l = lib;
                                break;
                            }
                        }
                        if (b != null && s != null && l != null) {
                            Request request = new Request(b, s, l, type);
                            library.addRequest(request);
                        }
                        break;
                    case "Borrows":
                        String[] borrowData = line.split(",");
                        Student std = library.getStudents().get(Integer.parseInt(borrowData[0]));
                        Book bk = library.getBooks().get(Integer.parseInt(borrowData[1]));

                        Librarian borrower = null;
                        Librarian returner = null;

                        for (Librarian lib : library.getLibrarians()) {
                            if (lib.getId() == Integer.parseInt(borrowData[4])) borrower = lib;
                            if (borrowData.length > 5 && !borrowData[5].isEmpty() &&
                                    lib.getId() == Integer.parseInt(borrowData[5])) returner = lib;
                        }
                        if (std != null && bk != null && borrower != null) {
                            Borrow borrow = new Borrow(bk, std, LocalDate.parse(borrowData[2]), borrower);
                            if (!borrowData[3].isEmpty()) {
                                borrow.setReturnDate(LocalDate.parse(borrowData[3]));
                            }
                            if (returner != null) {
                                borrow.setReturner(returner);
                            }
                            library.addBorrow(borrow);
                        }
                        break;
                    case "DelayedReturns":
                        String[] delayData = line.split(",");
                        Student stdd = library.getStudents().get(Integer.parseInt(delayData[0]));
                        Book bkk = library.getBooks().get(Integer.parseInt(delayData[1]));
                        Librarian borrowerr = null;
                        Librarian returnerr = null;

                        for (Librarian lib : library.getLibrarians()) {
                            if (lib.getId() == Integer.parseInt(delayData[4])) borrowerr = lib;
                            if (lib.getId() == Integer.parseInt(delayData[5])) returnerr = lib;
                        }

                        if (stdd != null && bkk != null && borrowerr != null && returnerr != null) {
                            Borrow borrow = new Borrow(bkk, stdd, LocalDate.parse(delayData[2]), borrowerr);
                            borrow.setReturnDate(LocalDate.parse(delayData[3]));
                            borrow.setReturner(returnerr);
                            library.addDelayedReturn(borrow);
                        }
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found.");
        }
    }
}