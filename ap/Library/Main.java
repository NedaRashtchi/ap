package ap.Library;

import java.util.Scanner;

public class Main {
    public static void main() {
        Library lib = new Library("library1");

        lib.addBook(new Book("book1" , "author1",2020,120 , 1));
        lib.addBook(new Book("book2" , "author2",2021,121, 2));
        lib.addBook(new Book("book3" , "author3",2022,122, 3));

        lib.addStudent(new Student("Neda","Rashtchi", 140301,"CE"));
        lib.addLibrarian("librarian1","number1" , 10001);
        lib.addLibrarian("librarian2","number2" , 10002);

        int choice = 0;

        do{
            choice = Menu.firstMenu();

                    if (choice == 1) {
                        Scanner in = new Scanner(System.in);
                        int index = lib.searchStudent(in.nextInt());
                        if(index != -1){
                            loop:
                            do{
                                int ch = Menu.studentMenu();
                                switch (ch){
                                    case 1:
                                        int bookCode = in.nextInt();
                                        lib.addBorrowRequest(new Request(lib.searchBook(bookCode),lib.getStudent(index) ,"Borrow"));
//                                      lib.getRequests();
                                        break ;
                                    case 2:
                                        int bookCode2 = in.nextInt();
                                        lib.addReturnRequest(new Request(lib.searchBook(bookCode2),lib.getStudent(index) ,"Return"));
                                        break;
                                    case 3:
                                        lib.getBookList();
                                        break;
                                    case 4:
                                        lib.getStudent(index).getBorrowedbooks();
                                        break;
                                    case 5:
                                        break loop;
                                    default:
                                        System.out.println("Invalid selection");
                                        break;
                                }
                                // in.close();
                            }while(true);
                        }


                    }
                    else if(choice == 2){
                        lib.addStudent();
                       // break;
                    }
                    else if(choice == 3){
                        Scanner in = new Scanner(System.in);
                        int index = lib.searchLibrarian(in.nextInt());
                        if(index != -1){
                            loop:
                            do{
                                switch (Menu.librarianMenu()){
                                    case 1:
                                        lib.getBorrowRequests();
                                        lib.getReturnRequests();
                                        break;
                                    case 2:
                                        lib.getBorroewdBooks();
                                        break;
                                    case 3:
                                        lib.getBookList();
                                        break;
                                    case 4:
                                        lib.addStudent();
                                        break;
                                    case 5:
                                        lib.getStudents();
                                        break;
                                    case 6:
                                        System.out.println("Returning to main menu.");
                                        break loop;
                                    default:
                                        System.out.println("Invalid Selection");
                                        break;
                                }
                            }while(true);

                           // break;
                        }
                    }
                    else if(choice > 4){
                        System.out.println("Invalid choice");
                    }
        }while(choice != 4);

        System.out.println("finish");
    }
}
