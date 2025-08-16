package ap.finalproject;

public class Manager {
    private String name;
    private String idNumber;
    private String password;

    public Manager(String name, String idNumber, String password) {
        this.name = name;
        this.idNumber = idNumber;
        this.password = password;
    }

    public String getIdNumber() {
        return idNumber;
    }
    public String getPassword(){
        return password;
    }
    @Override
    public String toString() {
        return "Name: " + name +
                " | Username: " + idNumber
                + " | Password: " + password;
    }
}
