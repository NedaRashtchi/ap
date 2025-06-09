package ap.Project;

import java.time.LocalDate;

public class Librarian extends Person {
    private int id;
    private LocalDate registerDate;
//    private List<Request> requests;
    private int borrowCount;
    private int returnCount;

    public Librarian(String firstName, String lastName, int id , int borrowCount, int returnCount) {
        super(firstName, lastName);
        this.id = id;
        this.registerDate = LocalDate.now();
//        requests = new ArrayList<>();
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
//    public void setId(int id) {
//        this.id = id;
//    }
    public void setName(String firstName, String lastName) {
        super.setName(firstName, lastName);
    }
//    public List<Request> getRequests() {
//        return requests;
//    }
//    public void addRequest(Request request) {
//        requests.add(request);
//    }
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
}