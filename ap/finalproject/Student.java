package ap.finalproject;

public class Student extends Person {
    private String studentId;
    private int totalLoans;
    private int pendingReturns;
    private int delayedReturns;
    private int totalDelayDays;
    private boolean active;

    public Student(String name, String studentId, String username, String password) {
        super(name, username, password);
        this.studentId = studentId;
        this.totalLoans = 0;
        this.pendingReturns = 0;
        this.delayedReturns = 0;
        this.totalDelayDays = 0;
        this.active = true;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public int getTotalDelayDays() {
        return totalDelayDays;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public void addDelayDays(int days) {
        this.totalDelayDays += days;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                " | Student ID: " + studentId +
                " | Username: " + username +
                " | Total Loans: " + totalLoans +
                " | Pending Returns: " + pendingReturns +
                " | Delayed Returns: " + delayedReturns +
                " | Total Delay Days: " + totalDelayDays +
                " | Active: " + (active ? "Yes" : "No");
    }
}