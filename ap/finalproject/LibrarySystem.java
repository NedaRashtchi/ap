package ap.finalproject;

// LibrarySystem.java
public class LibrarySystem {
    private StudentManager studentManager;
    private LibrarianManager librarianManager;
    private MenuHandler menuHandler;
    private Manager manager;

    public LibrarySystem(Manager manager) {
        this.manager = manager;
        this.studentManager = new StudentManager();
        this.librarianManager = new LibrarianManager();
        this.menuHandler = new MenuHandler(this);
    }

    public int getStudentCount() {
        return this.studentManager.getStudentCount();
    }

    public void registerStudent(String name, String studentId, String username, String password) {
        studentManager.registerStudent(name, studentId, username, password);
    }

    public void registerLibrarian(String name, String username, String password) {
        librarianManager.registerLibrarian(name, username, password);
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
        System.out.println("Not implemented.");
    }

    public void start() {
        menuHandler.displayMainMenu();
    }

    public static void main(String[] args) {
        LibrarySystem system = new LibrarySystem(new Manager("Manager", "manager", "1234"));
        system.start();
    }
}