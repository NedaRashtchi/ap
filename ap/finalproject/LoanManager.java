package ap.finalproject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class LoanManager {
    private List<Loan> loanRequests;
    private List<Loan> lentBooks;

    public LoanManager() {
        this.loanRequests = new ArrayList<>();
        this.lentBooks = new ArrayList<>();
    }

    public boolean requestLoan(Student student, Book book) {
        if (!student.isActive()) {
            System.out.println("Student is deactivated and cannot borrow books.");
            return false;
        }
        if (!book.getStatus().equals("Available")) {
            System.out.println("Book is not available for loan.");
            return false;
        }

        Loan newLoan = new Loan(student, book, LocalDate.now(), null, null, LoanStatus.REQUESTED);
        loanRequests.add(newLoan);
        book.setStatus("Requested");
        return true;
    }

    public boolean approveLoan(int loanIndex, Librarian librarian) {
        if (loanIndex < 0 || loanIndex >= loanRequests.size()) {
            return false;
        }
        Loan loan = loanRequests.get(loanIndex);
        if (loan.getStatus() != LoanStatus.REQUESTED) {
            return false;
        }
        loan.setStatus(LoanStatus.BORROWED);
        loan.setBorrowDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusDays(10));
        loan.getBook().setStatus("Borrowed");

        Student student = loan.getStudent();
        student.increaseTotalLoans();
        student.increasePendingReturns();

        librarian.addBooksLent();

        loanRequests.remove(loanIndex);
        lentBooks.add(loan);
        return true;
    }

    public List<Loan> getLoansByStudent(Student student) {
        List<Loan> studentLoans = new ArrayList<>();
        for (Loan loan : loanRequests) {
            if (loan.getStudent().equals(student)) {
                studentLoans.add(loan);
            }
        }
        for (Loan loan : lentBooks) {
            if (loan.getStudent().equals(student)) {
                studentLoans.add(loan);
            }
        }
        return studentLoans;
    }

    public List<Loan> getRequestedLoans() {
        return new ArrayList<>(loanRequests);
    }

    public boolean returnLoan(Loan loan, Librarian librarian) {
        if (loan.getStatus() != LoanStatus.BORROWED) {
            return false;
        }
        if (LocalDate.now().isAfter(loan.getReturnDate())) {
            int delayDays = (int) ChronoUnit.DAYS.between(loan.getReturnDate(), LocalDate.now());
            loan.getStudent().increaseDelayedReturns();
            loan.getStudent().addDelayDays(delayDays);
        }

        loan.setStatus(LoanStatus.RETURNED);
        loan.setReturnDate(LocalDate.now());
        loan.getBook().setStatus("Available");
        loan.getStudent().decreasePendingReturns();
        librarian.addBooksReturned();
        return true;
    }

    public List<Loan> getBorrowedLoans() {
        List<Loan> borrowedLoans = new ArrayList<>();
        for (Loan loan : lentBooks) {
            if (loan.getStatus() == LoanStatus.BORROWED) {
                borrowedLoans.add(loan);
            }
        }
        return borrowedLoans;
    }

    public List<Loan> getLoansInLastWeek() {
        List<Loan> recentLoans = new ArrayList<>();
        LocalDate oneWeekAgo = LocalDate.now().minusDays(7);

        for (Loan loan : lentBooks) {
            if (loan.getBorrowDate() != null &&
                    !loan.getBorrowDate().isBefore(oneWeekAgo) &&
                    !loan.getBorrowDate().isAfter(LocalDate.now())) {
                recentLoans.add(loan);
            }
        } return recentLoans;
    }

    public List<Loan> getLoanRequests() {
        return loanRequests;
    }

    public List<Loan> getLentBooks() {
        return lentBooks;
    }

    public List<Loan> getLoans() {
        List<Loan> allLoans = new ArrayList<>();
        allLoans.addAll(loanRequests);
        allLoans.addAll(lentBooks);
        return allLoans;
    }

    public void setLoans(List<Loan> loans) {

        this.loanRequests.clear();
        this.lentBooks.clear();

        for (Loan loan : loans) {
            if (loan.getStatus() == LoanStatus.REQUESTED) {
                this.loanRequests.add(loan);
            } else {
                this.lentBooks.add(loan);
            }
        }
    }
}