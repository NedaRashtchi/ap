package ap.exercises.ex4;

public class EmployeeTester {
    public static void main() {
        E3_12 harry = new E3_12("harry", 53400);

        System.out.println("Name: " + harry.getEmployeeName());
        System.out.println("Salary before raise: " + harry.getCurrentSalary());
        harry.raiseSalary(10);
        System.out.println("Salary after raise: " + harry.getCurrentSalary());
    }
}
