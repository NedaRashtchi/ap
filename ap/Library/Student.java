package ap.Library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Student extends User implements Loginable {
    private String major;
    private List<Loan> loanedBooks;
    private LocalDate registerDate;

    public Student(String firstName, String lastName, int stdNumber, String major) {
        super(firstName, lastName , stdNumber);
        this.major = major;
        this.loanedBooks = new ArrayList<>();
        this.registerDate = LocalDate.now();
    }

    public void addLoan(Loan loan) {
        loanedBooks.add(loan);
    }
    public void removeLoan(Loan loan) {
        loanedBooks.remove(loan);
    }
    public List<Loan> getLoanedBooks() {
        return loanedBooks;
    }
    public String getMajor() {
        return major;
    }
    public LocalDate getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "[Name: " + this.getName() + ", Student Number: " + super.getIdNumber() + ", Major: " + major + "]";
    }

}
