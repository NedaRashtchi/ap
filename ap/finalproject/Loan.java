package ap.finalproject;

import java.time.LocalDate;

public class Loan {
    private Student student;
    private Book book;
    private LocalDate requestDate;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LoanStatus status;

    public Loan(Student student, Book book, LocalDate requestDate, LocalDate borrowDate, LocalDate returnDate, LoanStatus status) {
        this.student = student;
        this.book = book;
        this.requestDate = requestDate;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public LocalDate getRequestDate() { return requestDate; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Student: " + student.getName() +
                " | Book: " + book.getTitle() +
                " | Request Date: " + requestDate +
                " | Borrow Date: " + borrowDate +
                " | Return Date: " + returnDate +
                " | Status: " + status;
    }
}