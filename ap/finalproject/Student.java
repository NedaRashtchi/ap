package ap.finalproject;

public class Student extends Person {
    private String studentId;
    private int totalLoans;
    private int pendingReturns;
    private int delayedReturns;

    public Student(String name, String studentId, String username, String password) {
        super(name, username, password);
        this.studentId = studentId;
        this.totalLoans = 0;
        this.pendingReturns = 0;
        this.delayedReturns = 0;
    }


    public int getTotalLoans() {
        return totalLoans;
    }

    public int getPendingReturns() {
        return pendingReturns;
    }

    public void setPendingReturns(int pendingReturns) {
        this.pendingReturns = pendingReturns;
    }

    public int getDelayedReturns() {
        return delayedReturns;
    }

    public void increaseTotalLoans() {
        this.totalLoans++;
    }

    public void increasePendingReturns() {
        this.pendingReturns++;
    }

    public void decreasePendingReturns() {
        this.pendingReturns--;
    }

    public void increaseDelayedReturns() {
        this.delayedReturns++;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                " | Student ID: " + studentId +
                " | Username: " + username +
                " | Total Loans: " + totalLoans +
                " | Pending Returns: " + pendingReturns +
                " | Delayed Returns: " + delayedReturns;
    }
}