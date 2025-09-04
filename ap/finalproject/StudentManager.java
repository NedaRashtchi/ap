package ap.finalproject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentManager {
    private List<Student> students;

    public StudentManager() {
        this.students = new ArrayList<>();
    }

    public void registerStudent(String name, String studentId, String username, String password) {
        if (isUsernameTaken(username)) {
            System.out.println("This username already exists. Please choose a different username.");
            return;
        }

        Student newStudent = new Student(name, studentId, username, password);
        students.add(newStudent);
        System.out.println("Student registration completed successfully.");
    }

    public Student authenticateStudent(String username, String password) {
        return students.stream()
                .filter(s -> s.getUsername().equals(username) && s.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void displayStudents() {
        System.out.println("\n--- List of Registered Students ---");

        if (students.isEmpty()) {
            System.out.println("No students have registered yet.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }
    }

    public List<Student> getTop10StudentsWithMostDelays() {
        return students.stream()
                .filter(s -> s.getDelayedReturns() > 0)
                .sorted(Comparator.comparingInt(Student::getDelayedReturns).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    private boolean isUsernameTaken(String username) {
        return students.stream().anyMatch(s -> s.getUsername().equals(username));
    }

    public int getStudentCount() {
        return students.size();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public boolean deactivateStudent(String username) {
        Student student = students.stream()
                .filter(s -> s.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        if (student != null) {
            student.setActive(false);
            return true;
        }
        return false;
    }

    public boolean activateStudent(String username) {
        Student student = students.stream()
                .filter(s -> s.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        if (student != null) {
            student.setActive(true);
            return true;
        }
        return false;
    }

    public boolean updateStudent(Student student, String newName, String newStudentId, String newUsername, String newPassword) {
        if (!student.getUsername().equals(newUsername) && isUsernameTaken(newUsername)) {
            System.out.println("This username already exists. Please choose a different username.");
            return false;
        }
        student.setName(newName);
        student.setStudentId(newStudentId);
        student.setUsername(newUsername);
        student.setPassword(newPassword);
        return true;
    }
}