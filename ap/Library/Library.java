package ap.Library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private String name;
    private Manager manager;
    private Map<Integer,Book> books;
    private Map<Integer,Student> students;
    private Map<Integer,Librarian> librarians;
    private List<Loan> loans;

    public Library(String name , Manager manager) {
        this.name = name;
        this.manager = manager;
        this.books = new HashMap<>();
        this.students = new HashMap<>();
        this.librarians = new HashMap<>();
        this.loans = new ArrayList<>();
    }

    public Student searchStudent(int stdNumber) {
        return students.get(stdNumber);
    }
    public Librarian searchLibrarian(int libNumber) {
        return librarians.get(libNumber);
    }
    public void addStudent(Student student) {
        students.put(student.getIdNumber(), student);
    }
    public void addLibrarian(Librarian librarian) {
        librarians.put(librarian.getIdNumber(), librarian);
    }
    public void addBook(Book book) {
        books.put(book.getBookCode(), book);
    }
    public Map<Integer, Student> getStudents() {
        return students;
    }
    public Map<Integer, Librarian> getLibrarians() {
        return librarians;
    }
    public String getName() {
        return name;
    }
    public Manager getManager() {
        return manager;
    }
    public List<Loan> getLoans() {
        return loans;
    }
    public Map<Integer, Book> getBooks() {
        return books;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setManager(Manager manager) {
        this.manager = manager;
    }
    public void setBooks(Map<Integer, Book> books) {
        this.books = books;
    }
    public void setStudents(Map<Integer, Student> students) {
        this.students = students;
    }
    public void setLibrarians(Map<Integer, Librarian> librarians) {
        this.librarians = librarians;
    }
    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
    public void printBookList() {
        books.values().forEach(System.out::println);
    }
}