package ap.Project;

import java.time.LocalDate;

public class Librarian extends Person {
    private int id;
    private LocalDate registerDate;

    public Librarian(String firstName, String lastName, int id) {
        super(firstName, lastName);
        this.id = id;
        this.registerDate = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(LocalDate date) {
        this.registerDate = date;
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