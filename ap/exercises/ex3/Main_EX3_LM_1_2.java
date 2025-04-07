package ap.exercises.ex3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main_EX3_LM_1_2{

    public static void main(String[] args) throws FileNotFoundException {
        EX3_LM_STUDENT [] students = new EX3_LM_STUDENT[3];
        students[0] = new EX3_LM_STUDENT("Neda","Rashtchi",140301,"Computer Programming");
        students[1] = new EX3_LM_STUDENT("Zahra","Rashtchi",140302,"Architecture");
        students[2] = new EX3_LM_STUDENT("Maryam","Rahnema",140303,"Literature");

        EX3_LM_BOOKS [] books = new EX3_LM_BOOKS[4];
        books[0] = new EX3_LM_BOOKS("Big Java","Cay S Horstmann",2021,1216);
        books[1] = new EX3_LM_BOOKS("Effective Java","Joshua Bloch",2018,416);
        books[2] = new EX3_LM_BOOKS("Discrete Mathematics","Kenneth H Rosen",2018,1200);
        books[3] = new EX3_LM_BOOKS("Java:The Complete Reference","Herbert Schildt",2020,1248);

        writeStudent(students);
        writeBook(books);

        EX3_LM_STUDENT [] studentFile = new EX3_LM_STUDENT[3];
        readStudent(studentFile);

        EX3_LM_BOOKS [] bookFile = new EX3_LM_BOOKS[4];
        readBook(bookFile);

        System.out.println("------Students------");
        printStudent(studentFile);
        System.out.println("------Books------");
        printBook(bookFile);

    }
    static void writeStudent(EX3_LM_STUDENT []student) {
        try{
            PrintWriter writer = new PrintWriter("students.txt");
            for (EX3_LM_STUDENT std : student) {
                writer.println(std.getFirstName());
                writer.println(std.getLastName());
                writer.println(std.getStdNumber());
                writer.println(std.getMajor());
            }
            writer.close();
        }catch(FileNotFoundException e){
            System.err.println("Error: unable to write to file.");
        }
    }
    static void writeBook(EX3_LM_BOOKS []book) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("books.txt");
            for (EX3_LM_BOOKS bk : book) {
                writer.println(bk.getName());
                writer.println(bk.getAuthor());
                writer.println(bk.getPublishYear());
                writer.println(bk.getPages());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: unable to write to file.");
        }
    }
    static void readStudent(EX3_LM_STUDENT []student){

        try {
            Scanner reader = new Scanner(new File("students.txt"));
            for (int i = 0; i < student.length; i++) {
                student[i] = new EX3_LM_STUDENT(reader.nextLine() , reader.nextLine(),
                        Integer.parseInt(reader.nextLine()) , reader.nextLine());
            }
            reader.close();
            System.out.println("Student info loaded");
        } catch (FileNotFoundException e) {
            System.err.println("Error: file not found.");
        } catch (NoSuchElementException e) {
            System.err.println("Error: incomplete data.");
        }
    }
    static void readBook(EX3_LM_BOOKS []book){
        try{
            Scanner reader = new Scanner(new File("books.txt"));
            for (int i = 0; i < book.length; i++) {
                book[i] = new EX3_LM_BOOKS(reader.nextLine() , reader.nextLine(),
                        Integer.parseInt(reader.nextLine()) , Integer.parseInt(reader.nextLine()));
            }
            reader.close();
            System.out.println("Book info loaded");
        }catch (FileNotFoundException e) {
            System.err.println("Error: file not found.");
        } catch (NoSuchElementException e) {
            System.err.println("Error: incomplete data.");
        }
    }
    static void printStudent(EX3_LM_STUDENT []student) {
        for (EX3_LM_STUDENT std : student) {
            System.out.println("Name :" + std.getFirstName() + " " + std.getLastName());
            System.out.println("Student Number: " + std.getStdNumber());
            System.out.println("Major : " +std.getMajor());
            System.out.println("------------------");
        }

    }
    static void printBook(EX3_LM_BOOKS []book) {
        for (EX3_LM_BOOKS bk : book) {
            System.out.println("Title : " +bk.getName());
            System.out.println("Author : " +bk.getAuthor());
            System.out.println("Publish Year: "+bk.getPublishYear());
            System.out.println("Number Of Pages: " + bk.getPages());
            System.out.println("------------------");
        }
    }
}
