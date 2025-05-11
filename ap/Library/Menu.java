package ap.Library;

import java.util.Scanner;

public class Menu {
    public int firstMenu() {
        System.out.println("Welcome to the Library\nAre you a :");
        System.out.println("1. Student\n2. Librarian\n3. Manager\n4. Exit");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        in.nextLine();
        switch(choice) {
            case 1:
                System.out.println("Are you registered ? (Y/N)");
                String answer = in.nextLine();
                if(answer.equals("Y") || answer.equals("y")){
                    System.out.print("Enter your Student number to login : ");
                    return 1;
                }else if(answer.equals("N") || answer.equals("n")){
                    System.out.println("Please enter your information to register.");
                    return 2;
                }
                break;
            case 2:
                System.out.println("Enter your id to login : ");
                return 3;
            case 3:
                break;
            case 4:
                return 4;
            default:
                break;
        }
        return 0;
    }
    public int studentMenu() {
        System.out.println("What do you want to do ?");
        System.out.println("1. Borrow Book\n2. Return Book\n3. List of the books\n4. Books you have borrowed\n5. Exit");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        in.nextLine();
        switch(choice) {
            case 1:
                System.out.println("Enter the book code to register your request.\nThen refer to the librarian to receive the book.");
                return 1;
            case 2:
                System.out.println("Enter the book code ,then refer to the librarian to return the book.");
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                System.out.println("Returning to main menu.");
                return 5;

        }
        return 0;
    }
    public int librarianMenu() {
        System.out.println("What do you want to do?");
        System.out.println("1. Request List\n2. Borrowed Books list\n3. All Books\n4. Add Student\n5. List of students\n6. Exit");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        in.nextLine();
        return choice;
//        switch(choice) {
//            case 1:
//                return 1;
//            case 2:
//                return 2;
//            case 3:
//                return 3;
//            case 4:
//                return 4;
//            case 5:
//                return 5;
//            case 6:
//                System.out.println("Returning to main menu.");
//                return 6;
//            default:
//                System.out.println("Invalid Selection");
//                break;
//        }
//        return 0;
    }
}
