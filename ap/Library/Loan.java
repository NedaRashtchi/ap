package ap.Library;

import java.time.LocalDate;

public class Loan {

    public static final int loanDuration = 10;
    private Book book;
    private Student student;
    private Librarian librarian;
    private LocalDate loanDate;
    private LoanType type;

    public Loan(Book book, Student student, Librarian librarian , LoanType type) {
        this.book = book;
        this.student = student;
        this.librarian = librarian;
        this.loanDate = LocalDate.now();
        this.type = type;
    }
    public void setLoanDuration() {
        loanDate = LocalDate.now();
    }
    @Override
    public String toString() {
        return book + "\n" + student + "\n" + librarian;
    }
}
