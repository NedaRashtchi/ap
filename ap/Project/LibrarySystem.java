package ap.Project;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
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
        Librarian librarian = library.searchLibrarian(id);
        if ( librarian != null) {
            currentLibrarian = librarian;
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
                writer.println(student.getStdNumber() + "," +
                        student.getName() + "," +
                        student.getMajor() + "," +
                        student.getRegisterDate());
            }

            writer.println("Librarians:");
            for (Librarian librarian : library.getLibrarians().values()) {
                writer.println(librarian.getId() + "," +
                        librarian.getName() + "," +
                        librarian.getRegisterDate());
            }

            writer.println("Manager:");
            Manager manager = library.getManager();
            writer.println(manager.getId() + "," +
                    manager.getName() + "," +
                    manager.getEducation());

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
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found.");
        }
    }
}