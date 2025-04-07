package ap.exercises.ex3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
    }
    static void writeStudent(EX3_LM_STUDENT []student) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("students.txt");
        for (EX3_LM_STUDENT std : student) {
            writer.println(std.getFirstName());
            writer.println(std.getLastName());
            writer.println(std.getStdNumber());
            writer.println(std.getMajor());
        }
        writer.close();
    }
    static void writeBook(EX3_LM_BOOKS []book) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("books.txt");
        for (EX3_LM_BOOKS bk : book) {
            writer.println(bk.getName());
            writer.println(bk.getAuthor());
            writer.println(bk.getPublishYear());
            writer.println(bk.getPages());
        }
        writer.close();
    }
    static void readStudent(EX3_LM_STUDENT []student) throws FileNotFoundException {
        Scanner reader = new Scanner(new File("students.txt"));
        for (int i = 0; i < student.length; i++) {
            student[i] = new EX3_LM_STUDENT(reader.nextLine() , reader.nextLine(),
                                    Integer.parseInt(reader.nextLine()) , reader.nextLine());
        }
        reader.close();
        System.out.println("Student info loaded");
    }
    static void readBook(EX3_LM_BOOKS []book) throws FileNotFoundException {
        Scanner reader = new Scanner(new File("books.txt"));
        for (int i = 0; i < book.length; i++) {
            book[i] = new EX3_LM_BOOKS(reader.nextLine() , reader.nextLine(),
                            Integer.parseInt(reader.nextLine()) , Integer.parseInt(reader.nextLine()));
        }
        reader.close();
        System.out.println("Book info loaded");
    }
}

