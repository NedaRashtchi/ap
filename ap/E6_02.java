package ap;

import java.util.Scanner;

public class E6_02 {
    public static void main(String[] args){
        System.out.print("Enter  a sequence of integers (enter 0 to quit) : ");
        int [] seq = new int [100];
        int len = 0;
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < 100; i++){
            seq[i] = in.nextInt();
            len++;
            if(seq[i]==0) break;
        }

        maxMin(seq , len); // a
        oddEven(seq , len); // b
        cumulative(seq , len); // c
        dupl(seq , len); //d
    }
    static void maxMin(int [] seq , int len){ // part (a)
        int max = seq[0];
        for (int i=1 ; i< len ; i++){
            if(seq[i]!=0 && seq[i]>max) max = seq[i];
        }
        int min = seq[0];
        for(int i=1 ; i<len ; i++){
            if(seq[i]!=0 && seq[i]<min) min = seq[i];
        }
        System.out.format("The maximum number : %d\nThe minimum number is : %d\n",max, min);
    }
    static void oddEven(int [] seq, int len){ // part(b)
        int odd =0 ;
        int even =0 ;
        for(int i=0 ; i<len ; i++){
            if(seq[i]!=0){
                if (seq[i] % 2 == 0) even++;
                else odd++;
            }
        }
        System.out.println("number of even numbers : "+even);
        System.out.println("number of odd numbers : "+odd);
    }
    static void cumulative(int [] seq , int len){ //part(c)
        int sum = seq[0];
        System.out.print("Cumulative totals : "+sum);
        for(int i=1; i<len-1 ; i++){
            sum+=seq[i];
            System.out.print(" "+sum);
        }
        System.out.print("\n");
    }
    static void  dupl(int [] seq , int len){
        boolean duplicate = false;
        int checked = 0;
        int i=0;
        System.out.print("All adjacent duplicates : ");
        while(i<len-1){
            if(seq[i]==seq[i+1] && seq[i]!=checked){
                checked = seq[i];
                System.out.print(seq[i]+" ");
                i+=2;
                duplicate = true;
            }else if(seq[i]!=seq[i+1]) i++;
        }
        if(!duplicate) System.out.print("There is no adjacent duplicate in the sequence!");

    }
}
