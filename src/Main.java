import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("what is your name?");
        String name = sc.nextLine();
        System.out.format("hello %s" , name);

    }
}