package ap.Project;

import java.util.ArrayList;
import java.util.HashMap;

public class Library {
    private String name;
    private HashMap<Integer , Book> books;
    private HashMap<Integer,Student> students;
    private HashMap<Integer,Librarian> librarians;
    private Manager manager;

    public Library(String name, Manager manager) {
        this.name = name;
        this.books = new HashMap<>();
        this.students = new HashMap<>();
        this.librarians = new HashMap<>();
        this.manager = manager;
    }

    public HashMap<Integer, Book> getBooks() {
        return books;
    }

    public HashMap<Integer, Student> getStudents() {
        return students;
    }

    public HashMap<Integer, Librarian> getLibrarians() {
        return librarians;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void addBook(Book book) {
        books.put(book.getBookCode(), book);
    }

    public void addStudent(Student student) {
        students.put(student.getStdNumber(), student);
    }

    public void addLibrarian(Librarian librarian) {
        librarians.put(librarian.getId(), librarian);
    }

    public Book searchBook(int code) {
        return books.get(code);
    }
    public ArrayList<Book> searchBooksByTitle(String title) { // ???
        ArrayList<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
    }

//    public List<Book> searchBooksByTitle(String title) {
//        List<Book> result = new ArrayList<>();
//        for (Book b : books) {
//            if (b.getTitle().toLowerCase().contains(title.toLowerCase())) {
//                result.add(b);
//            }
//        }
//        return result;
//    }

    public Student searchStudent(int stdNumber) {
        return students.get(stdNumber);
    }

    public Librarian searchLibrarian(int id) {
        return librarians.get(id);
    }

    public void printBookList() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book book : books.values()) {
                System.out.println(book);
            }
        }
    }
}
