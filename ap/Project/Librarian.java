package ap.Project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Librarian extends Person {
    private int id;
    private LocalDate registerDate;
    private List<Request> requests;

    public Librarian(String firstName, String lastName, int id) {
        super(firstName, lastName);
        this.id = id;
        this.registerDate = LocalDate.now();
        requests = new ArrayList<>();
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
    public List<Request> getRequests() {
        return requests;
    }
    public void addAssignedRequest(Request request) {
        requests.add(request);
    }

    @Override
    public String toString() {
        return "[Name: " + getName() + ", ID: " + id + "]";
    }
}