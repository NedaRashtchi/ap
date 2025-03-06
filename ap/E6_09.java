package ap;

import java.util.Scanner;

public class E6_09 {
    public static void main(String[] args) {
        System.out.println("input string : ");
        Scanner s = new Scanner(System.in);
        String str = s.nextLine();

        for(int i=str.length()-1 ; i>=0 ; i--){
            System.out.print(str.charAt(i));
        }
    }
}
