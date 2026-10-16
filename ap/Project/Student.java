package ap.Project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person implements Loginable {
    private int stdNumber;
    private String major;
    // rebuilt from active borrows on load; must not be serialized (would duplicate entries)
    private transient List<Book> borrowedBooks;
    private LocalDate registerDate;

    public Student(String firstName, String lastName, int stdNumber, String major) {
        super(firstName, lastName);
        this.stdNumber = stdNumber;
        this.major = major;
        this.borrowedBooks = new ArrayList<>();
        this.registerDate = LocalDate.now();
    }

    public int getStdNumber() {
        return stdNumber;
    }
    public String getMajor() {
        return major;
    }
    public LocalDate getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(LocalDate date) {
        this.registerDate = date;
    }

    public void addBorrowedBook(Book book) {
        if (borrowedBooks == null) borrowedBooks = new ArrayList<>();
        borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        if (borrowedBooks == null) return;
        borrowedBooks.remove(book);
    }
    public List<Book> getBorrowedBooks() {
        if (borrowedBooks == null) borrowedBooks = new ArrayList<>();
        return borrowedBooks;
    }

    public void listBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("No books borrowed.");
        } else {
            for (Book b : borrowedBooks) {
                System.out.println(b);
            }
        }
    }

    @Override
    public String toString() {
        return "[Name: " + super.toString() + ", Student Number: " + stdNumber + ", Major: " + major + "]";
    }

    @Override
    public boolean login(int id) {
        return this.stdNumber == id;
    }
}