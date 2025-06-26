package ap.Project;

import java.time.LocalDate;

public class Request extends Action {
    private Librarian librarian;
    private RequestType type;

    public Request(Book book, Student student , Librarian librarian, RequestType type){
        super(book, student, LocalDate.now());
        this.librarian = librarian;
        this.type = type;
    }

    public Librarian getLibrarian(){
        return librarian;
    }
    public Student getStudent(){
        return super.getStudent();
    }
    public Book getBook(){
        return super.getBook();
    }
    public RequestType getType(){
        return type;
    }

    @Override
    public String toString(){
        return "Book: " + super.getBook() +
                "\nStudent: " + super.getStudent() +
                "\nLibrarian: " + librarian +
                "\nRequest Type: " + type;
    }
}