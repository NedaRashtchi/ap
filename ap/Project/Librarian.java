package ap.Project;

import java.time.LocalDate;

public class Librarian extends Person implements Loginable {
    private int id;
    private LocalDate registerDate;
    private int borrowCount;
    private int returnCount;

    public Librarian(String firstName, String lastName, int id , int borrowCount, int returnCount) {
        super(firstName, lastName);
        this.id = id;
        this.registerDate = LocalDate.now();
        this.borrowCount = borrowCount;
        this.returnCount = returnCount;
    }

    public int getId() {
        return id;
    }
    public LocalDate getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(LocalDate date) {
        this.registerDate = date;
    }

    public void setName(String firstName, String lastName) {
        super.setName(firstName, lastName);
    }

    public void addBorrowCount() {
        borrowCount++;
    }
    public void addReturnCount() {
        returnCount++;
    }
    public int getBorrowCount() {
        return borrowCount;
    }
    public int getReturnCount() {
        return returnCount;
    }

    @Override
    public String toString() {
        return "[Name: " + super.toString() + ", ID: " + id +
                ", Borrow Count: " + borrowCount + ", Return Count: " + returnCount + "]";
    }
    @Override
    public boolean login(int id) {
        return this.id == id;
    }
}