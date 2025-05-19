package ap.Project;

public class Manager extends Person {
    private Education education;
    private int id;

    public Manager(String firstName, String lastName, Education education, int id) {
        super(firstName, lastName);
        this.education = education;
        this.id = id;
    }

    public Education getEducation() {
        return education;
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[Name: " + getName() + ", ID: " + id + ", Education: " + education + "]";
    }
}