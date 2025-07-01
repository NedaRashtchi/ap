package ap.Library;


public class Manager extends User implements Loginable {
    private Education education;

    public Manager(String firstName, String lastName, Education education, int id) {
        super(firstName, lastName, id);
        this.education = education;
    }

    @Override
    public String toString() {
        return "[Name: " + super.getName() + ", ID: " + super.getIdNumber() + ", Education: " + education + "]";
    }
}