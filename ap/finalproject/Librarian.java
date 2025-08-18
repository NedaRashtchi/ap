package ap.finalproject;

public class Librarian extends Person {
    public Librarian(String name, String username, String password) {
        super(name, username, password);
    }

    @Override
    public String toString() {
        return "Name: " + name +
                " | Username: " + username;
    }
}