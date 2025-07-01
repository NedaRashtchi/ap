package ap.Library;

public class User implements Loginable {
    private String firstName;
    private String lastName;
    private int idNumber;

    public User(String firstName, String lastName, int idNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
    }

    public int getIdNumber() {
        return idNumber;
    }
    public String getName() {
        return firstName + " " + lastName;
    }
    public void setName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    @Override
    public String toString() {
        return "[ Name: " + this.getName() + ", ID Number: " + idNumber + " ]";
    }
    @Override
    public boolean login(int id) {
        return this.idNumber == id;
    }

}
