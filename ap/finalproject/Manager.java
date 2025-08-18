package ap.finalproject;

public class Manager extends Person {
    public Manager(String name, String username, String password) {
        super(name, username, password);
    }

    @Override
    public String toString() {
        return "Name: " + name +
                " | Username: " + username;
    }
}