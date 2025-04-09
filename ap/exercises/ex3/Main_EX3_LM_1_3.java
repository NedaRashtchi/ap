package ap.exercises.ex3;

import java.util.Scanner;

public class Main_EX3_LM_1_3 {
    private static EX3_LM_STUDENT [] students;
    public static void main(String[] args) {
        students = new EX3_LM_STUDENT[5];
        students[0] = new EX3_LM_STUDENT("Neda","Rashtchi",140301,"Computer Programming");
        students[1] = new EX3_LM_STUDENT("Zahra","Rashtchi",140302,"Architecture");
        students[2] = new EX3_LM_STUDENT("Maryam","Rahnema",140303,"Literature");
        students[3] = new EX3_LM_STUDENT("Masoud","Rashtchi",140304,"Mathematics");
        students[4] = new EX3_LM_STUDENT("Yekta","Saedian",140305,"Computer Programming");

        Scanner in = new Scanner(System.in);
        System.out.println("1. Search by name.\n2. Search by Student number.");
        switch (in.nextInt()) {

            case 1:
                in.nextLine();
                System.out.println("Enter student's name (Ali Mohammadi): ");
                String name = in.nextLine();
                searchByName(name);
                break;
            case 2:
                System.out.println("Enter student's number(140300): ");
                int studentNumber = in.nextInt();
                searchByNumber(studentNumber);
                break;
            default:
                System.out.println("Invalid input.");
                break;
        }

    }
    static void searchByName(String name) {
        String [] names = name.split(" ");
        for(EX3_LM_STUDENT s : students){
           if(s.getFirstName().equalsIgnoreCase(names[0])
                   && s.getLastName().equalsIgnoreCase(names[1])){
               System.out.println("Student found");
               System.out.println("Name :" + s.getFirstName() + " " + s.getLastName());
               System.out.println("Student Number: " + s.getStdNumber());
               System.out.println("Major : " +s.getMajor());
               return;
           }
        }
        System.out.println("Student not found");
    }
    static void searchByNumber(int studentNumber) {
        for(EX3_LM_STUDENT s : students){
            if(s.getStdNumber() == studentNumber){
                System.out.println("Student found");
                System.out.println("Name :" + s.getFirstName() + " " + s.getLastName());
                System.out.println("Student Number: " + s.getStdNumber());
                System.out.println("Major : " +s.getMajor());
                return;
            }
        }
        System.out.println("Student not found");
    }
}
