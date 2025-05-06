package ap.quizzz;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Book> books = new ArrayList<Book>();
        ArrayList<Pen> pens = new ArrayList<>();
        books.add(new Book(120, "b1" , 10));
        books.add(new Book(200, "b2", 10));
        pens.add(new Pen(10 , "red" , "bic" , 10));
        pens.add(new Pen(20 , "green" , "bic" , 10));

//        System.out.println(books);
//        System.out.println(pens);
        for (Book b : books) {
            System.out.println(b);
        }
        for (Pen p : pens) {
            System.out.println(p);
        }
    }
}
