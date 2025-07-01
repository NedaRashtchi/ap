package ap.Library;

public class Book {
    private String title;
    private String author;
    private int publicationYear;
    private int pageCount;
    private int bookCode;

    public Book(String title, String author, int publicationYear, int pageCount, int bookCode) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.pageCount = pageCount;
        this.bookCode = bookCode;
    }

    @Override
    public String toString() {
        return "[ Title: " + title + ", Author: " + author + ", Year: " + publicationYear +
                ", Pages: " + pageCount + ", Code: " + bookCode + " ]";
    }
}
