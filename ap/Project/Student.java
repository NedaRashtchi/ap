package ap.Project;

import java.time.LocalDate;
import java.util.ArrayList;

public class Student extends Person {
    private int stdNumber;
    private String major;
    private ArrayList<Book> borrowedBooks;
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
        borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
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
        return "[Name: " + getName() + ", Student Number: " + stdNumber + ", Major: " + major + "]";
    }

}
