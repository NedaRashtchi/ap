package ap.Library;

import java.time.LocalDate;

public class Loan {

    public static final int loanDuration = 10;
    private Book book;
    private Student student;
    private Librarian librarian;
    private LocalDate loanDate;
    private LoanType type;

    public Loan(Book book, Student student, Librarian librarian, LoanType type) {
        this.book = book;
        this.student = student;
        this.librarian = librarian;
        this.loanDate = LocalDate.now();
        this.type = type;
    }

    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Librarian getLibrarian() {
        return librarian;
    }
    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }
    public LocalDate getLoanDate() {
        return loanDate;
    }
    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }
    public LoanType getType() {
        return type;
    }
    public void setType(LoanType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return book + "\n" + student + "\n" + librarian;
    }
}