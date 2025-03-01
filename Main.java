import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        System.out.print("Enter any number: ");
        Scanner in = new Scanner(System.in);
        double number = in.nextDouble();
        if(number == 0) {
            System.out.println("The number is zero");
        }else if(number > 0) {
            System.out.println("The number is positive");
        }else if(number < 0) {
            System.out.println("The number is negative");
        }
        if(number < 1) {
            System.out.println("It's a small number");
        }
        if(number > 1000000) {
            System.out.println("It's a large number");
        }

    }
}