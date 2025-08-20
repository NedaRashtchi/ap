package ap.finalproject;

import java.util.List;

// LibrarySystem.java
public class LibrarySystem {
    private StudentManager studentManager;
    private LibrarianManager librarianManager;
    private BookManager bookManager;
    private MenuHandler menuHandler;
    private Manager manager;

    public LibrarySystem(Manager manager) {
        this.manager = manager;
        this.studentManager = new StudentManager();
        this.librarianManager = new LibrarianManager();
        this.bookManager = new BookManager();
        this.menuHandler = new MenuHandler(this);
        FileHandler.loadData(this);
    }

    public StudentManager getStudentManager() {
        return studentManager;
    }

    public LibrarianManager getLibrarianManager() {
        return librarianManager;
    }

    public BookManager getBookManager() {
        return bookManager;
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

    public void registerStudent(String name, String studentId, String username, String password) {
        studentManager.registerStudent(name, studentId, username, password);
        saveData();
    }

    public void registerLibrarian(String name, String username, String password) {
        librarianManager.registerLibrarian(name, username, password);
        saveData();
    }

    public void addBook(String title, String author, int publicationYear, int pageCount, int bookCode) {
        bookManager.addBook(title, author, publicationYear, pageCount, bookCode);
        saveData();
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

    public void editStudentInformation(Student student) {
        System.out.println("Not implemented.");
    }

    public void borrowBook(Student student) {
        System.out.println("Not implemented.");
    }

    public void returnBook(Student student) {
        System.out.println("Not implemented.");
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