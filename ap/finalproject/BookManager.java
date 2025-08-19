package ap.finalproject;

import java.util.ArrayList;
import java.util.List;

public class BookManager {
    private List<Book> books;

    public BookManager() {
        this.books = new ArrayList<>();
    }

    public void addBook(String title, String author, int publicationYear, int pageCount, int bookCode) {
        if (isBookCodeTaken(bookCode)) {
            System.out.println("This book code already exists. Choose a different code.");
            return;
        }

        Book newBook = new Book(title, author, publicationYear, pageCount, bookCode);
        books.add(newBook);
        System.out.println("Book added successfully.");
    }

    public void displayBooks() {
        System.out.println("\n--- List of Available Books ---");

        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }

    private boolean isBookCodeTaken(int bookCode) {
        return books.stream().anyMatch(b -> b.getBookCode() == bookCode);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}