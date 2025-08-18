package ap.finalproject;

import java.util.ArrayList;
import java.util.List;

public class LibrarianManager {
    private List<Librarian> librarians;

    public LibrarianManager() {
        this.librarians = new ArrayList<>();
    }

    public void registerLibrarian(String name, String username, String password) {
        if (isUsernameTaken(username)) {
            System.out.println("This username already exists. Please choose a different username.");
            return;
        }

        Librarian newLibrarian = new Librarian(name, username, password);
        librarians.add(newLibrarian);
        System.out.println("Librarian registration completed successfully.");
    }

    public Librarian authenticateLibrarian(String username, String password) {
        return librarians.stream()
                .filter(l -> l.getUsername().equals(username) && l.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    private boolean isUsernameTaken(String username) {
        return librarians.stream().anyMatch(l -> l.getUsername().equals(username));
    }
}