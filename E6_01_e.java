import java.util.Scanner;

public class E6_01_e {
    public static void main(String[] args) {
        Scanner number = new Scanner(System.in);
        System.out.println("Enter number: ");
        int num = number.nextInt();
        int sum = 0;
        while(num>0){
            if((num%10)%2!=0){
                sum+=num%10;
            }
            num/=10;
        }
        System.out.println("Sum of odd digits : "+sum);
    }
}
