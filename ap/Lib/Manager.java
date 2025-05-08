package ap.Lib;

public class Manager extends Person{

    private String education; // make enum

    public Manager(String firstName, String lastName, String education) {
        super(firstName, lastName);
        this.education = education;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
