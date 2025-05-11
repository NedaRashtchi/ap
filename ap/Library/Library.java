package ap.Library;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {
    private String name;
    private List<Book> books;
    private List<Student> students;
    private List<Librarian> librarians;
    private List<Book> borrowedBooks ; // make method
    private List<Request> borrowRequests ;
    private List<Request> returnRequests ;

    public Library(String name) {
        this.name = name;
        books = new ArrayList<>();
        students = new ArrayList<>();
        librarians = new ArrayList<>();
        borrowedBooks = new ArrayList<>();
        borrowRequests = new ArrayList<>();
        returnRequests = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void getBookList() {
        for (Book b : books) {
            System.out.println(b.toString());
        }
    }
    public void getStudents(){ // return Arraylist?
        for (Student s : students) {
            System.out.println(s.toString());
        }
    }
    public void getBorrowRequests(){
        if (borrowRequests.isEmpty()){
            System.out.println("No requests.");
        }
        int i = 0;
        for (Request r : borrowRequests) {
            i++;
            System.out.println( i +". " + r.toString());
        }
    }
    public void getReturnRequests(){
        if (returnRequests.isEmpty()){
            System.out.println("No requests.");
        }
        int i = 0;
        for (Request r : returnRequests) {
            i++;
            System.out.println( i +". " + r.toString());
        }
    }
    public void getBorroewdBooks(){
        if(borrowedBooks.isEmpty()){
            System.out.println("No Books borrowed.");
        }
        for (Book b : borrowedBooks) {
            System.out.println(b.toString());
        }
    }
    public List<Librarian> getLibrarians() {
        return librarians;
    }
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    void addStudent() {
        Scanner in = new Scanner(System.in);
        System.out.print("First Name: ");
        String firstname = in.nextLine();
        System.out.print("Last Name: ");
        String lastname = in.nextLine();
        System.out.print("Major: ");
        String major = in.nextLine();
     //   in.close();
        int stdnum = 140301 + students.size();
        students.add(new Student(firstname,lastname,stdnum,major));
    }
    void addStudent(Student student) {
        students.add(student);
    }
    void addLibrarian(String firstname, String lastname , int id) {
        librarians.add(new Librarian(firstname, lastname, id));
    }
    private void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
    }
    public void addBook(Book book) {
        books.add(book);
    }
    void addBorrowRequest(Request request) {
        borrowRequests.add(request);
        System.out.println("Request added successfully.\n" + request.toString());
    }
    void addReturnRequest(Request request) {
        returnRequests.add(request);
        System.out.println("Request added successfully.\n" + request.toString());
    }

    Student getStudent(int index){
        return students.get(index);
    }

    Book searchBook(int bookCode) {
        for (Book b : books) {
            if(bookCode == b.getBookCode()){
                return b;
            }
        }
        return null;
    }
    int searchStudent(int number){
        int index = 0;
        for (Student s : students) {
            if(number == s.getStdNumber()) return index;
            index++;
        }
        System.out.println("Student not found");
        return -1;
    }
    int searchLibrarian(int number){
        int index = 0;
        for (Librarian l : librarians) {
            if(number == l.getId()) return index;
            index++;
        }
        System.out.println("Invalid id.");
        return -1;
    }
}
