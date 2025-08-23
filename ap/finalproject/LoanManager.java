package ap.finalproject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanManager {
    private List<Loan> loans;

    public LoanManager() {
        this.loans = new ArrayList<>();
    }

    public boolean requestLoan(Student student, Book book) {
        if (!book.getStatus().equals("Available")) {
            System.out.println("Book is not available for loan.");
            return false;
        }

        Loan newLoan = new Loan(student, book, LocalDate.now(), null, null, LoanStatus.REQUESTED);
        loans.add(newLoan);
        book.setStatus("Requested");
        return true;
    }

    public boolean approveLoan(int loanIndex, Librarian librarian) {
        if (loanIndex < 0 || loanIndex >= loans.size()) {
            return false;
        }
        Loan loan = loans.get(loanIndex);
        if (loan.getStatus() != LoanStatus.REQUESTED) {
            return false;
        }
        loan.setStatus(LoanStatus.BORROWED);
        loan.setBorrowDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusDays(10));
        loan.getBook().setStatus("Borrowed");
        librarian.addBooksLent();
        return true;
    }

    public List<Loan> getLoansByStudent(Student student) {
        List<Loan> studentLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getStudent().equals(student)) {
                studentLoans.add(loan);
            }
        }
        return studentLoans;
    }

    public List<Loan> getRequestedLoans() {
        List<Loan> requestedLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getStatus() == LoanStatus.REQUESTED) {
                requestedLoans.add(loan);
            }
        }
        return requestedLoans;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}