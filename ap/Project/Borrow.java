package ap.Project;

import java.time.LocalDate;

public class Borrow extends Action {
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Librarian borrower;
    private Librarian returner;

    public Borrow(Book book, Student student, LocalDate borrowDate ,Librarian borrower) {
        super(book, student, borrowDate);
        this.dueDate = borrowDate.plusDays(10);
        this.returnDate = null;
        this.borrower = borrower;
        this.returner = null;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public void setBorrower(Librarian borrower) {
        this.borrower = borrower;
    }
    public void setReturner(Librarian returner){
        this.returner = returner;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    public Book getBook() {
        return super.getBook();
    }
    public Student getStudent() {
        return super.getStudent();
    }
    public LocalDate getBorrowDate() {
        return super.getActionDate();
    }
    public LocalDate getDueDate() {
        // Gson bypasses the constructor and older saved files have no dueDate;
        // fall back to borrowDate + 10 days.
        if (dueDate == null) {
            dueDate = getActionDate().plusDays(10);
        }
        return dueDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public Librarian getBorrower() {
        return borrower;
    }
    public Librarian getReturner() {
        return returner;
    }

    @Override
    public String toString() {
        return "BOOK " + book + "\nSTUDENT " + student +
                "\nBORROW DATE: " + super.getActionDate() +
                "\nDUE DATE: " + dueDate +
                "\nRETURN DATE: " + (returnDate != null ? returnDate : "not returned yet");
    }
}