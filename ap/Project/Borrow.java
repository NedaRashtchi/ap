package ap.Project;

import java.time.LocalDate;

public class Borrow {
    private Book book;
    private Student student;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private Librarian borrower; // change names
    private Librarian returner;

    public Borrow(Book book, Student student, LocalDate borrowDate ,Librarian borrower) {
        this.book = book;
        this.student = student;
        this.borrowDate = borrowDate;
        this.returnDate = borrowDate.plusDays(10);
        this.borrower = borrower;
        this.returner = null;
    }
//    public Borrow(Book book, Student student, LocalDate borrowDate ,Librarian borrower , Librarian returner) {
//        this(book,student,borrowDate,borrower);
//        this.returner = borrower;
//    }

    public void setReturner(Librarian returner){
        this.returner = returner;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    public Book getBook() {
        return book;
    }
    public Student getStudent() {
        return student;
    }
    public LocalDate getBorrowDate() {
        return borrowDate;
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
        return "BOOK " + book + "\nSTUDENT " + student +"\nBORROW DATE :" + borrowDate + "\nRETURN DATE :" + returnDate;
    }

}