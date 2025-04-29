package ap.exercises.ex4;

public class E3_12 {
    private final String employeeName;
    private double currentSalary;

    public E3_12(String employeeName, double currentSalary) {
        this.employeeName = employeeName;
        this.currentSalary = currentSalary;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public double getCurrentSalary() {
        return currentSalary;
    }

    public void raiseSalary(double byPercent) {
        currentSalary += (currentSalary * byPercent * 0.01);
    }
}
