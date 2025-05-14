package ap.Project;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private String name;
    private List<Book> books;
    private List<Student> students;
    private List<Librarian> librarians;
    private Manager manager;

    public Library(String name, Manager manager) {
        this.name = name;
        this.books = new ArrayList<>();
        this.students = new ArrayList<>();
        this.librarians = new ArrayList<>();
        this.manager = manager;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Librarian> getLibrarians() {
        return librarians;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
    }

    public Book searchBook(int code) {
        for (Book b : books) {
            if (b.getBookCode() == code) {
                return b;
            }
        }
        return null;
    }

    public List<Book> searchBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
    }

    public int searchStudent(int number) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStdNumber() == number) {
                return i;
            }
        }
        return -1;
    }

    public int searchLibrarian(int number) {
        for (int i = 0; i < librarians.size(); i++) {
            if (librarians.get(i).getId() == number) {
                return i;
            }
        }
        return -1;
    }

    public void printBookList() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }
}
