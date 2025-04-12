package ap.exercises.ex3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main_EX3_LM_2_1 {
    public static void main(String[] args) throws FileNotFoundException {
        EX3_LM_STUDENT [] students = new EX3_LM_STUDENT[5];
        students[0] = new EX3_LM_STUDENT("Neda","Rashtchi",140301,"Computer Programming");
        students[1] = new EX3_LM_STUDENT("Zahra","Rashtchi",140302,"Architecture");
        students[2] = new EX3_LM_STUDENT("Maryam","Rahnema",140303,"Literature");
        students[3] = new EX3_LM_STUDENT("Masoud","Rashtchi",140304,"Mathematics");
        students[4] = new EX3_LM_STUDENT("Yekta","Saedian",140305,"Computer Programming");


        EX3_LM_BOOKS [] books = new EX3_LM_BOOKS[4];
        books[0] = new EX3_LM_BOOKS("Big Java","Cay S Horstmann",2021,1216);
        books[1] = new EX3_LM_BOOKS("Effective Java","Joshua Bloch",2018,416);
        books[2] = new EX3_LM_BOOKS("Discrete Mathematics","Kenneth H Rosen",2018,1200);
        books[3] = new EX3_LM_BOOKS("Java:The Complete Reference","Herbert Schildt",2020,1248);

        EX3_LM_BORROW [] borrows = new EX3_LM_BORROW[2];
        borrows[0] = new EX3_LM_BORROW("Discrete Mathematics",140305,"1404/1/22","1404/1/29");

        Scanner in = new Scanner(System.in);
        System.out.print("Enter student number: ");
        int studentNumber = in.nextInt();
        in.nextLine();
        if(search(studentNumber,students)){
            System.out.print("Enter book title: ");
            String bookName = in.nextLine();
            System.out.println("Borrow date : ");
            String borrowDate = in.nextLine();
            System.out.println("Return date : ");
            String returnDate = in.nextLine();

            borrows[1] = new EX3_LM_BORROW(bookName,studentNumber,borrowDate,returnDate);
            writeBorrowed(borrows);
        }

        EX3_LM_BORROW [] borrowsFile = new EX3_LM_BORROW[2];
       readBorrowed(borrowsFile);
       System.out.format("Student %d borrowed book %s\n" , borrowsFile[0].getStudentNumber(), borrowsFile[0].getBookName());
        System.out.format("Student %d borrowed book %s\n" , borrowsFile[1].getStudentNumber(), borrowsFile[1].getBookName());

    }
    static void writeBorrowed(EX3_LM_BORROW[]borrow) {
        try {
            PrintWriter writer = new PrintWriter("borrowed.txt");
            for (EX3_LM_BORROW b : borrow) {
                writer.println(b.getBookName());
                 writer.println(b.getStudentNumber());
                writer.println(b.getBorrowDate());
                writer.println(b.getReturnDate());
            }
            writer.close();
            System.out.println("Loan info saved");
        } catch (FileNotFoundException e) {
            System.err.println("Error: unable to write to file.");
        }
    }

    static void readBorrowed(EX3_LM_BORROW[] borrow) {
        try {
            Scanner reader = new Scanner(new File("borrowed.txt"));
            for (int i = 0; i < borrow.length; i++) {
                borrow[i] = new EX3_LM_BORROW(
                        reader.nextLine(),
                        Integer.parseInt(reader.nextLine()),
                        reader.nextLine(),
                        reader.nextLine()
                );
            }
            reader.close();
            System.out.println("info loaded");
        } catch (FileNotFoundException e) {
            System.err.println("Error: file not found.");
        } catch (NoSuchElementException e) {
            System.err.println("Error: incomplete data.");
        }
    }

    static boolean search(int studentNumber, EX3_LM_STUDENT [] students) {
        for(EX3_LM_STUDENT s : students){
            if(s.getStdNumber() == studentNumber){
                System.out.println("Student found");
                return true;
            }
        }
        System.out.println("Student not found");
        return false;
    }
}
