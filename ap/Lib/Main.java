package ap.Lib;

import java.util.Scanner;

public class Main {
    public static void main() {
        Library lib = new Library("library1");

        lib.addBook(new Book("book1" , "author1",2020,120 , 1));
        lib.addBook(new Book("book2" , "author2",2021,121, 2));
        lib.addBook(new Book("book3" , "author3",2022,122, 3));

        lib.addStudent(new Student("Neda","Rashtchi", 140301,"CE"));

        int choice = 0;
        outer:
        do{
            choice = Menu.firstMenu();

                if (choice == 1) {
                    Scanner in = new Scanner(System.in);
                    int index = lib.searchStudent(in.nextInt());
                    if(index != -1){
                        switch (Menu.studentMenu()){
                            case 1:
                                int bookCode = in.nextInt();
                                lib.addRequest(new Request(lib.searchBook(bookCode),lib.getStudent(index) ,true));
//                            lib.getRequests();
                                break;
                            case 2:
                                int bookCode2 = in.nextInt();
                                lib.addRequest(new Request(lib.searchBook(bookCode2),lib.getStudent(index) ,false));
                                break;
                            case 3:
                                lib.getBookList();
                                break;
                            case 4:
                                lib.getStudent(index).getBorrowedbooks();
                                break;
                            case 5:
                                break;
                        }
                       // in.close();
                    }

                }
                else if(choice == 2){
                    lib.addStudent();
                }
                else if(choice == 3){

                }
                else {
                    System.out.println("Invalid choice");
                }


        }while(choice != 4);

        System.out.println("finish");
    }
}
