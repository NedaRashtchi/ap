package ap.Library;

import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    private String name;
    private ArrayList<Book> books;
    private ArrayList<Student> students;
    private ArrayList<Librarian> librarians;
    private ArrayList<Book> borrowedBooks ; // make method
    private ArrayList<Request> borrowRequests ;

    public Library(String name) {
        this.name = name;
        books = new ArrayList<>();
        students = new ArrayList<>();
        librarians = new ArrayList<>();
        borrowedBooks = new ArrayList<>();
        borrowRequests = new ArrayList<>();
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
    public void getRequests(){
        for (Request r : borrowRequests) {
            System.out.println(r.toString());
        }
    }
    public ArrayList<Librarian> getLibrarians() {
        return librarians;
    }
    public ArrayList<Book> getBorrowedBooks() {
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
    private void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
    }
    public void addBook(Book book) {
        books.add(book);
    }
    void addRequest(Request request) {
        borrowRequests.add(request);
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
}
