package ap.exercises.ex3;

public class EX3_LM_BOOKS {
    private String name;
    private String author;
    private int publishYear;
    private int pages;

    public EX3_LM_BOOKS(String name, String author, int publishYear, int pages) {
        this.name = name;
        this.author = author;
        this.publishYear = publishYear;
        this.pages = pages;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getPublishYear() {
        return publishYear;
    }
    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }
    public int getPages() {
        return pages;
    }
    public void setPages(int pages) {
        this.pages = pages;
    }
}
