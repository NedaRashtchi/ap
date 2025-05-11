package ap.Library;

public class Book {
    private String title;
    private String author;
    private int publishYear;
    private int pages;
    private int bookCode;

    public Book(String title, String author, int publishYear, int pages , int bookCode) {
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.pages = pages;
        this.bookCode = bookCode;
    }
    public String gettitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public int getPublishYear() {
        return publishYear;
    }
    public void settitle(String title) {
        this.title = title;
    }
    public int getPages() {
        return pages;
    }
    public int getBookCode() {
        return bookCode;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }
    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "[title: " + title + ", Author: " + author +
                ", Publish Year: " + publishYear + ", Pages: "+pages+" Book Code: "+bookCode+"]";
    }
}
