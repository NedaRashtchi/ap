package ap.Project;

public class Librarian extends Person {
    private int id;

    public Librarian(String firstName, String lastName, int id) {
        super(firstName, lastName);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String firstName, String lastName) {
        super.setName(firstName, lastName);
    }

    @Override
    public String toString() {
        return "[Name: " + getName() + ", ID: " + id + "]";
    }
}