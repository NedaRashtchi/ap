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

    //add , search , print methods ...
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
    public Manager getManager() {
        return manager;
    }
    public void printBookList() {
        books.values().forEach(System.out::println);
    }
}