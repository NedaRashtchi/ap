package ap.finalproject.ManageSystem;

import ap.finalproject.*;
import ap.finalproject.FileHandling.FileHandler;
import ap.finalproject.Menu.MenuHandler;

import java.util.List;

// LibrarySystem.java
public class LibrarySystem {
    private StudentManager studentManager;
    private LibrarianManager librarianManager;
    private BookManager bookManager;
    private LoanManager loanManager;
    private MenuHandler menuHandler;
    private Manager manager;

    public LibrarySystem(Manager manager) {
        this.manager = manager;
        this.studentManager = new StudentManager();
        this.librarianManager = new LibrarianManager();
        this.bookManager = new BookManager();
        this.loanManager = new LoanManager();
        this.menuHandler = new MenuHandler(this);
        FileHandler.loadData(this);
    }

    public StudentManager getStudentManager() {
        return studentManager;
    }

    public LibrarianManager getLibrarianManager() {
        return librarianManager;
    }

    public Librarian getLibrarianByUsername(String username) {
        return librarianManager.getLibrarians().stream()
                .filter(l -> l.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public BookManager getBookManager() {
        return bookManager;
    }
    public LoanManager getLoanManager() {
        return loanManager;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public int getStudentCount() {
        return this.studentManager.getStudentCount();
    }
    public int getBookCount() {
        return this.bookManager.getBookCount();
    }

    public void registerStudent(String name, String studentId, String username, String password) {
        studentManager.registerStudent(name, studentId, username, password);
        saveData();
    }

    public void registerLibrarian(String name, String username, String password) {
        librarianManager.registerLibrarian(name, username, password);
        saveData();
    }

    public boolean addBook(String title, String author, int publicationYear, int pageCount, int bookCode) {
        boolean success = bookManager.addBook(title, author, publicationYear, pageCount, bookCode);
        if (success) {
            saveData();
        }
        return success;
    }

    public Student authenticateStudent(String username, String password) {
        return studentManager.authenticateStudent(username, password);
    }

    public Librarian authenticateLibrarian(String username, String password) {
        return librarianManager.authenticateLibrarian(username, password);
    }

    public Boolean authenticateManager(String username, String password) {
        if (username.equals(manager.getUsername()) && password.equals(manager.getPassword())) {
            return true;
        } else {
            System.out.println("Invalid username or password");
            return false;
        }
    }

    public boolean changeLibrarianPassword(String username, String oldPassword, String newPassword) {
        boolean result = librarianManager.changePassword(username, oldPassword, newPassword);
        if (result) {
            saveData();
        }
        return result;
    }

    public boolean deactivateStudent(String username) {
        boolean success = studentManager.deactivateStudent(username);
        if (success) {
            saveData();
        }
        return success;
    }

    public boolean activateStudent(String username) {
        boolean success = studentManager.activateStudent(username);
        if (success) {
            saveData();
        }
        return success;
    }

    public boolean updateStudent(Student student, String newName, String newStudentId, String newUsername, String newPassword) {
        boolean success = studentManager.updateStudent(student, newName, newStudentId, newUsername, newPassword);
        if (success) {
            saveData();
        }
        return success;
    }

    public boolean requestLoan(Student student, Book book) {
        boolean success = loanManager.requestLoan(student, book);
        if (success) {
            saveData();
        }
        return success;
    }

    public boolean approveLoan(int loanIndex, Librarian librarian) {
        boolean success = loanManager.approveLoan(loanIndex, librarian);
        if (success) {
            saveData();
        }
        return success;
    }

    public boolean returnBook(Loan loan, Librarian librarian) {
        boolean success = loanManager.returnLoan(loan, librarian);
        if (success) {
            saveData();
        }
        return success;
    }

    public void displayAvailableBooks() {
        bookManager.displayBooks();
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookManager.searchBooksByTitle(title);
    }

    public Book searchBookByBookCode(int bookCode) {
        return bookManager.searchBookByBookCode(bookCode);
    }

    public List<Book> searchBooksByAuthor(String author) {
        return bookManager.searchBooksByAuthor(author);
    }

    public List<Book> searchBooksByPublicationYear(int publicationYear) {
        return bookManager.searchBooksByPublicationYear(publicationYear);
    }

    public boolean isBookCodeTaken(int bookCode, Book excludeBook) {
        return bookManager.isBookCodeTaken(bookCode, excludeBook);
    }

    public List<Loan> getLoansInLastWeek() {
        return loanManager.getLoansInLastWeek();
    }

    public List<Student> getTop10StudentsWithMostDelays() {
        return studentManager.getTop10StudentsWithMostDelays();
    }

    public void saveData() {
        FileHandler.saveData(this);
    }

    public void exit() {
        saveData();
        System.out.println("Data saved. Exiting system. Goodbye!");
        System.exit(0);
    }

    public void start() {
        menuHandler.displayMainMenu();
    }

    public static void main(String[] args) {
        LibrarySystem system = new LibrarySystem(new Manager("Manager", "manager", "1234"));
        system.start();
    }
}