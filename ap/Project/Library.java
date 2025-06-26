package ap.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private String name;
    private Map<Integer, Book> books;
    private Map<Integer, Student> students = new HashMap<>();
    private List<Librarian> librarians;
    private Manager manager;
    private List<Request> requests;
    private List<Borrow> borrows;
    private List<Borrow> borrowedRecords;
    private List<Borrow> delayedReturns;

    public Library(String name, Manager manager) {
        this.name = name;
        this.books = new HashMap<>();
        this.librarians = new ArrayList<>();
        this.manager = manager;
        this.requests = new ArrayList<>();
        this.borrows = new ArrayList<>();
        this.borrowedRecords = new ArrayList<>();
        this.delayedReturns = new ArrayList<>();
    }

    public Map<Integer, Book> getBooks() {
        return books;
    }

    public Map<Integer, Student> getStudents() {
        return students;
    }

    public List<Librarian> getLibrarians() {
        return librarians;
    }

    public Manager getManager() {
        return manager;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public List<Borrow> getBorrows() {
        return borrows;
    }

    public List<Borrow> getBorrowedRecords() {
        return borrowedRecords;
    }

    public List<Borrow> getDelayedReturns() {
        return delayedReturns;
    }

    public List<Request> getRequestsByLibrarian(int librarianId) {
        List<Request> result = new ArrayList<>();
        for (Request request : requests) {
            if (request.getLibrarian() != null && request.getLibrarian().getId() == librarianId) {
                result.add(request);
            }
        }
        return result;
    }

    public List<Request> getRequestsByStudent(int stdNumber) {
        List<Request> result = new ArrayList<>();
        for (Request request : requests) {
            if (request.getStudent() != null && request.getStudent().getStdNumber() == stdNumber) {
                result.add(request);
            }
        }
        return result;
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

    public Student searchStudent(int stdNumber) {
        return students.get(stdNumber);
    }

    public void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
    }

    public void addRequest(Request request) {
        requests.add(request);
    }

    public void addBorrow(Borrow borrow) {
        borrows.add(borrow);
    }

    public void addBorrowRecord(Borrow borrow) {
        borrowedRecords.add(borrow);
    }

    public void addDelayedReturn(Borrow delayedReturn) {
        delayedReturns.add(delayedReturn);
    }

    public int searchLibrarian(int number) {
        for (int i = 0; i < librarians.size(); i++) {
            if (librarians.get(i).getId() == number) {
                return i;
            }
        }
        return -1;
    }

    public Book searchBook(int code) {
        return books.get(code);
    }

    public List<Book> searchBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
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