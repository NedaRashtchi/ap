package ap.Project;

import java.time.LocalDate;

public class Borrow extends Action {
    private LocalDate returnDate;
    private Librarian borrower;
    private Librarian returner;

    public Borrow(Book book, Student student, LocalDate borrowDate ,Librarian borrower) {
        super(book, student, borrowDate);
        this.returnDate = borrowDate.plusDays(10);
        this.borrower = borrower;
        this.returner = null;
    }

    public void setReturner(Librarian returner){
        this.returner = returner;
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
                "\nRETURN DATE: " + returnDate;
    }
}