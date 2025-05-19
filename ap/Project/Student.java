package ap.Project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private int stdNumber;
    private String major;
    private List<Book> borrowedBooks;
    private LocalDate registerDate;
    private List<Request> studentRequests;

    public Student(String firstName, String lastName, int stdNumber, String major) {
        super(firstName, lastName);
        this.stdNumber = stdNumber;
        this.major = major;
        this.borrowedBooks = new ArrayList<>();
        this.registerDate = LocalDate.now();
        this.studentRequests = new ArrayList<>();
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
    public List<Book> getBorrowedBooks() {
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

    public void addRequest(Request request) {
        studentRequests.add(request);
    }

    public List<Request> getPendingRequests() {
        return studentRequests;
    }

    @Override
    public String toString() {
        return "[Name: " + getName() + ", Student Number: " + stdNumber + ", Major: " + major + "]";
    }

}
