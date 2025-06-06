package ap.Library;

public class Librarian extends Person{

    private int id;

    public Librarian(String firstName, String lastName, int id) {
        super(firstName,lastName);
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "[Name: " + super.toString() + "ID: " + id + "]";
    }
}
