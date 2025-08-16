package ap.finalproject;

public class Librarian {
    String name;
    String username;
    String password;

    public Librarian(String name, String idNumber, String password) {
        this.name = name;
        this.username = idNumber;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    @Override
    public String toString() {
        return "Name: " + name +
                " | Username: " + username
                + " | Password: " + password;
    }
}
