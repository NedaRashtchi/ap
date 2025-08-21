package ap.finalproject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookManager {
    private List<Book> books;

    public BookManager() {
        this.books = new ArrayList<>();
    }

    public boolean addBook(String title, String author, int publicationYear, int pageCount, int bookCode) {
        if (isBookCodeTaken(bookCode)) {
            System.out.println("This book code already exists. Choose a different code.");
            return false;
        }

        Book newBook = new Book(title, author, publicationYear, pageCount, bookCode);
        books.add(newBook);
        System.out.println("Book added successfully.");
        return true;
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

    boolean isBookCodeTaken(int bookCode) {
        return books.stream().anyMatch(b -> b.getBookCode() == bookCode);
    }


    public List<Book> searchBooksByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Book searchBookByBookCode(int bookCode) {
        return books.stream()
                .filter(book -> book.getBookCode() == bookCode)
                .findFirst()
                .orElse(null);
    }
    public List<Book> searchBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> searchBooksByPublicationYear(int publicationYear) {
        return books.stream()
                .filter(book -> book.getPublicationYear() == publicationYear)
                .collect(Collectors.toList());
    }

    public boolean isBookCodeTaken(int bookCode, Book excludeBook) {
        if (excludeBook == null) {
            return isBookCodeTaken(bookCode);
        }
        return books.stream()
                .filter(b -> b != excludeBook)
                .anyMatch(b -> b.getBookCode() == bookCode);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getBookCount() {
        return books.size();
    }
}