package ap.exercises.ex1;

import java.util.Scanner;

public class E6_03 {
    public static void main(String[] args) {
        System.out.println("input a string : ");
        Scanner input = new Scanner(System.in);
        String string = input.nextLine();

        findUppercase(string);
        findSecondLetter(string);
        replaceVowel(string);
        findVowel(string);

    }
    static void findUppercase(String string){ // a
        System.out.print("uppercase letters : ");
        for(int i=0 ; i<string.length();i++){
            if(Character.isUpperCase(string.charAt(i))){
                System.out.print(string.charAt(i) + " ");
            }
        }
        System.out.println();
    }
    static void findSecondLetter(String string){ //b
        String[] newstring = string.split(" ");
        System.out.print("each second letter : ");
        for(int i=0 ; i<newstring.length ; i++){
            if(newstring[i].length()>1){
                System.out.print(newstring[i].charAt(1) + " ");
            }
        }
        System.out.println();
    }
    static void replaceVowel(String string){ //c
        String replaced = "";
        char replacing ;
        for(int i=0 ; i<string.length() ; i++){
            replacing = string.charAt(i);
            switch(replacing){
                case 'a' , 'e' , 'i' , 'o' , 'u' , 'A' , 'E' , 'I' , 'O' :
                    replaced += '_' ;
                    break;
                default :
                    replaced += replacing;
                    break;
            }
        }
        System.out.println("no vowels : "+replaced);
    }
    static void findVowel(String string){ // d, e
        int count = 0;
        System.out.println("position of the vowels : ");
        for(int i=0 ; i<string.length() ; i++){
            switch(string.charAt(i)){
                case 'a' , 'e' , 'i' , 'o' , 'u' , 'A' , 'E' , 'I' , 'O' :
                    count++;
                    System.out.print(string.charAt(i));
                    System.out.print("(" + i + ")  ");
                    break;
                default :  break;
            }
        }
        System.out.println("\nnumber of the vowels : "+count);
    }
}
