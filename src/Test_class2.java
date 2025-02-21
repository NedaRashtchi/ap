import java.util.*;
public class Test_class2 {
    public static void main(String[] args) {
        System.out.println("do you know my name?");
        Scanner sc = new Scanner(System.in);
        String ans = sc.next();
        if(ans.startsWith("y")){
            System.out.println("ok");
        }else if(ans.startsWith("n")){
            System.out.println("i am neda");
        }
    }
}
