package ap.Library;

public class Book {
    private String name;
    private String author;
    private int publishYear;
    private int pages;
    private int bookCode;

    public Book(String name, String author, int publishYear, int pages , int bookCode) {
        this.name = name;
        this.author = author;
        this.publishYear = publishYear;
        this.pages = pages;
        this.bookCode = bookCode;
    }
    String getName() {
        return name;
    }
    void setName(String name) {
        this.name = name;
    }
    String getAuthor() {
        return author;
    }
    void setAuthor(String author) {
        this.author = author;
    }
    int getPublishYear() {
        return publishYear;
    }
    void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }
     int getPages() {
        return pages;
    }
    void setPages(int pages) {
        this.pages = pages;
    }
    int getBookCode() {
        return bookCode;
    }

    @Override
    public String toString() {
        return "[Name: " + name + ", Author: " + author +
                ", Publish Year: " + publishYear + ", Pages: "+pages+" Book Code: "+bookCode+"]";
    }
}
