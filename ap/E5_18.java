package ap;

import java.util.*;

public class E5_18 {
    public static void main(String[] args) {
        System.out.println("Enter 3 strings: ");
        Scanner s = new Scanner(System.in);
        String[] name = new String[3];
        for (int i = 0; i < name.length; i++) {
            name[i] = s.nextLine();
        }
 /*       String temp;
        for(int j=0;j<name.length;j++){
            for(int i=0;i<name.length-1;i++){
                if(name[i].compareTo(name[i+1])>0){
                    temp = name[i];
                    name[i] = name[i+1];
                    name[i+1] = temp;
                }
            }
        }*/

        Arrays.sort(name);
        for(int i=0;i< name.length ;i++){
            System.out.println(name[i]);
        }
    }
}

