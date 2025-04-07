package ap.exercises.ex3;

public class EX3_LM_STUDENT {
    private String firstName;
     private String lastName;
     private int stdNumber;
    private String major;

    public EX3_LM_STUDENT(String firstName, String lastName, int stdNumber, String major) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.stdNumber = stdNumber;
        this.major = major;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getStdNumber() {
        return stdNumber;
    }
    public void setStdNumber(int stdNumber) {
        this.stdNumber = stdNumber;
    }
    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
}
