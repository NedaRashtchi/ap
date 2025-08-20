package ap.exercises.ex1;

import java.util.Scanner;

public class E6_08 {
    public static void main(String[] args) {
        System.out.println("input string : ");
        String str ;
        Scanner s = new Scanner(System.in);
        str = s.nextLine();
        for(int i=0;i<str.length();i++){
            System.out.println(str.charAt(i));
        }
    }
}
