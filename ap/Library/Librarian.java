package ap.Library;

import java.time.LocalDate;

public class Librarian extends User implements Loginable {

    private LocalDate registerDate;
    private int borrowCount;
    private int returnCount;

    public Librarian(String firstName, String lastName, int id) {
        super(firstName, lastName,id);
        this.registerDate = LocalDate.now();
        this.borrowCount = 0;
        this.returnCount = 0;
    }

    public void setName(String firstName, String lastName) {
        super.setName(firstName, lastName);
    }
    public LocalDate getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }
    public int getBorrowCount() {
        return borrowCount;
    }
    public int getReturnCount() {
        return returnCount;
    }
    @Override
    public String toString() {
        return "[Name: " + this.getName() + ", ID: " + super.getIdNumber() +
                ", Borrow Count: " + borrowCount + ", Return Count: " + returnCount + "]";
    }
}

