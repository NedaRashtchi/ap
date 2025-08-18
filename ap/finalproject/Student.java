package ap.finalproject;

public class Student extends Person {
    private String studentId;

    public Student(String name, String studentId, String username, String password) {
        super(name, username, password);
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                " | Student ID: " + studentId +
                " | Username: " + username;
    }
}