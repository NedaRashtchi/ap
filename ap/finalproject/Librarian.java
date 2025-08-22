package ap.finalproject;

public class Librarian extends Person {
    private int booksAdded;
    private int booksLent;
    private int booksReturned;

    public Librarian(String name, String username, String password) {
        super(name, username, password);
        this.booksAdded = 0;
        this.booksLent = 0;
        this.booksReturned = 0;
    }

    public void addBooksAdded() {
        this.booksAdded++;
    }

    public void addBooksLent() {
        this.booksLent++;
    }

    public void addBooksReturned() {
        this.booksReturned++;
    }

    public int getBooksAdded() {
        return booksAdded;
    }

    public int getBooksLent() {
        return booksLent;
    }

    public int getBooksReturned() {
        return booksReturned;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                " | Username: " + username +
                " | Books Added: " + booksAdded +
                " | Books Lent: " + booksLent +
                " | Books Returned: " + booksReturned;
    }
}