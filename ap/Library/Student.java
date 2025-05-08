package ap.Library;

import java.util.ArrayList;

public class Student extends Person {

    private int stdNumber;
    private String major;
    private ArrayList<Book> borowedbooks;

    public Student (String firstName, String lastName, int stdNumber, String major) {
        super(firstName,lastName);
        this.stdNumber = stdNumber;
        this.major = major;
        this.borowedbooks = new ArrayList<>();
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
    public void getBorrowedbooks() {
        if(borowedbooks.isEmpty()){
            System.out.println("No books found.");
        }else{
            for (Book b : borowedbooks) {
                System.out.println(b);
            }
        }

    }
    void addBorrowedbook(Book b) {
        borowedbooks.add(b);
    }
    void removeBorrowedbook(Book b) {
        borowedbooks.remove(b);
    }

    @Override
    public String toString() {
        return "[ Name: " + super.getFirstName() +" "+ super.getLastName() +
                ", Student Number:" + stdNumber + ", Major=" + major + "]";
    }

}
