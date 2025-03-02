import java.util.Scanner;
public class E5_02 {
    public static void main(String[] args){
        System.out.print("Enter any number: ");
        Scanner in = new Scanner(System.in);
        double number = in.nextDouble();
        if(number == 0){
            System.out.print("The number is zero");
        }else if(number > 0){
            System.out.print("The number is positive");
        }else if(number < 0){
            System.out.print("The number is negative");
        }
        if(Math.abs(number)<1){
            System.out.println(" (small)");
        }
        if(Math.abs(number)>1000000){
            System.out.println(" (large)");
        }

    }
}