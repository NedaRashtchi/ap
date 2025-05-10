package ap.Library;

public class Request {
    private Book book;
    private Student student;
 //   private boolean type; // true for borrow , false for return
    private String type;

    public Request(Book book, Student student , String type) {
        this.book = book;
        this.student = student;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Book: " + book + "\nStudent: " + student;
    }
}
