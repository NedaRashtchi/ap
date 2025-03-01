import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sa = new Scanner(System.in);
        System.out.println("Enter your salary: ");
        double salary = sa.nextDouble();

        double tax = 0;

        if(salary > 500000) {
            tax += (salary-500000)*0.06;
            salary -= salary-500000;
        }
        if(salary > 250000){
            tax += (salary-250000)*0.05;
            salary -= salary-250000;
        }
        if(salary > 100000){
            tax += (salary-100000)*0.04;
            salary -= salary-100000;
        }
        if(salary > 75000){
            tax += (salary-75000)*0.03;
            salary -= salary-75000;
        }
        if(salary > 50000){
            tax += (salary-50000)*0.02;
            salary -= salary-50000;
        }
        if(salary <= 50000){
            tax += salary*0.01;
        }
        System.out.println(" Your Tax: " + tax);

    }
}