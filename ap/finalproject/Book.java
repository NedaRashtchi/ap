package ap.finalproject;

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

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getBookCode() {
        return bookCode;
    }

    @Override
    public String toString() {
        return "Title: " + title +
                " | Author: " + author +
                " | Year: " + publicationYear +
                " | Pages: " + pageCount +
                " | Code: " + bookCode;
    }
}