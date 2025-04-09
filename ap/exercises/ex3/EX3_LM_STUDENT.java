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
    String getFirstName() {
        return firstName;
    }
    void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    String getLastName() {
        return lastName;
    }
    void setLastName(String lastName) {
        this.lastName = lastName;
    }
    int getStdNumber() {
        return stdNumber;
    }
    void setStdNumber(int stdNumber) {
        this.stdNumber = stdNumber;
    }
    String getMajor() {
        return major;
    }
    void setMajor(String major) {
        this.major = major;
    }
}
