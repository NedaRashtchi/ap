package ap.Project;

public class Request {
    private Book book;
    private Student student;
    private Librarian librarian;
    private RequestType type;

    public Request(Book book, Student student ,Librarian librarian , RequestType type){
        this.book = book;
        this.student = student;
        this.librarian = librarian;
        this.type = type;
    }

    public Librarian getLibrarian(){
        return librarian;
    }
    public Student getStudent(){
        return student;
    }
    public Book getBook(){
        return book;
    }
    public RequestType getType(){
        return type;
    }

    @Override
    public String toString(){
        return "Book: " + book + "\nStudent: " + student + "\nLibrarian: " + librarian + "\nRequest Type: " + type;
    }
}
