package ap.Project;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class FileHandler {
    public void saveLibraryData(Library library) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("library_data.txt"))) {

            writer.println("Books:");
            for (Book book : library.getBooks().values()) {
                writer.println(book.getTitle() + "," + book.getAuthor() + "," +
                        book.getPublicationYear() + "," + book.getPageCount() + "," +
                        book.getBookCode());
            }

            writer.println("Students:");
            for (Student student : library.getStudents().values()) {
                StringBuilder borrowedCodes = new StringBuilder();

                for (int i = 0; i < student.getBorrowedBooks().size(); i++) {
                    Book book = student.getBorrowedBooks().get(i);
                    borrowedCodes.append(book.getBookCode());

                    if (i < student.getBorrowedBooks().size() - 1) {
                        borrowedCodes.append(":");
                    }
                }

                writer.println(student.getStdNumber() + "," + student.getName() + "," +
                        student.getMajor() + "," + student.getRegisterDate() + "," +
                        borrowedCodes);
            }


            writer.println("Librarians:");
            for (Librarian librarian : library.getLibrarians()) {
                writer.println(librarian.getId() + "," + librarian.getName() + "," +
                        librarian.getRegisterDate() + "," + librarian.getBorrowCount() + "," +
                        librarian.getReturnCount());
            }

            writer.println("Manager:");
            Manager manager = library.getManager();
            writer.println(manager.getId() + "," +
                    manager.getName() + "," +
                    manager.getEducation());

            writer.println("Requests:");
            for (Request request : library.getRequests()) {
                int stdNumber = request.getStudent() != null ? request.getStudent().getStdNumber() : -1;
                int libId = request.getLibrarian() != null ? request.getLibrarian().getId() : -1;
                int bookCode = request.getBook() != null ? request.getBook().getBookCode() : -1;

                writer.println(stdNumber + "," + libId + "," + bookCode + "," + request.getType());
            }

            writer.println("Borrows:");
            for (Borrow borrow : library.getBorrows()) {
                writer.println(borrow.getStudent().getStdNumber() + "," + borrow.getBook().getBookCode() + "," +
                        borrow.getBorrowDate() + "," +
                        (borrow.getReturnDate() != null ? borrow.getReturnDate() : "") + "," +
                        borrow.getBorrower().getId() + "," +
                        (borrow.getReturner() != null ? borrow.getReturner().getId() : ""));
            }

            writer.println("BorrowRecords:");
            for (Borrow borrow : library.getBorrowedRecords()) {
                writer.println(borrow.getStudent().getStdNumber() + "," + borrow.getBook().getBookCode() + "," +
                        borrow.getBorrowDate() + "," +
                        (borrow.getReturnDate() != null ? borrow.getReturnDate() : "") + "," +
                        borrow.getBorrower().getId() + "," +
                        (borrow.getReturner() != null ? borrow.getReturner().getId() : ""));
            }

            writer.println("DelayedReturns:");
            for (Borrow borrow : library.getDelayedReturns()) {
                writer.println(borrow.getStudent().getStdNumber() + "," + borrow.getBook().getBookCode() + "," +
                        borrow.getBorrowDate() + "," + borrow.getReturnDate() + "," +
                        borrow.getBorrower().getId() + "," + borrow.getReturner().getId());
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadLibraryData(Library library) {
        try (Scanner fileScanner = new Scanner(new File("library_data.txt"))) {
            String section = "";

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                switch (line) {
                    case "Books:" :
                        section = "Books";
                        continue;
                    case "Students:" :
                        section = "Students";
                        continue;
                    case "Librarians:" :
                        section = "Librarians";
                        continue;
                    case "Manager:" :
                        section = "Manager";
                        continue;
                    case "Requests:" :
                        section = "Requests";
                        continue;
                    case "Borrows:" :
                        section = "Borrows";
                        continue;
                    case "BorrowRecords:" :
                        section = "BorrowRecords";
                        continue;
                    case "DelayedReturns:" :
                        section = "DelayedReturns";
                        continue;
                }

                if (line.trim().isEmpty()) continue;

                switch (section) {
                    case "Books":
                        String[] bookData = line.split(",");
                        Book book = new Book(bookData[0], bookData[1], Integer.parseInt(bookData[2]),
                                Integer.parseInt(bookData[3]), Integer.parseInt(bookData[4]));

                        library.addBook(book);
                        break;
                    case "Students":
                        String[] stdData = line.split(",");
                        Student student = new Student(stdData[1].split(" ")[0], stdData[1].split(" ")[1],
                                Integer.parseInt(stdData[0]), stdData[2]);

                        student.setRegisterDate(LocalDate.parse(stdData[3]));

                        if (stdData.length > 4 && !stdData[4].isEmpty()) {
                            String[] codeStrings = stdData[4].split(":");
                            for (String codeStr : codeStrings) {
                                int bookCode = Integer.parseInt(codeStr);
                                Book b = library.searchBook(bookCode);
                                if (b != null) {
                                    student.addBorrowedBook(b);
                                }
                            }
                        }
                        library.addStudent(student);
                        break;
                    case "Librarians":
                        String[] libData = line.split(",");
                        Librarian librarian = new Librarian(libData[1].split(" ")[0],
                                libData[1].split(" ")[1], Integer.parseInt(libData[0]),
                                Integer.parseInt(libData[3]), Integer.parseInt(libData[4]));
                                librarian.setRegisterDate(LocalDate.parse(libData[2]));

                        library.addLibrarian(librarian);
                        break;
                    case "Manager":
                        String[] mgrData = line.split(",");
                        Manager manager = new Manager(mgrData[1].split(" ")[0],
                                mgrData[1].split(" ")[1], Education.valueOf(mgrData[2]),
                                Integer.parseInt(mgrData[0]));

                        library.setManager(manager);
                        break;
                    case "Requests":
                        String[] reqData = line.split(",");
                        int stdNumber = Integer.parseInt(reqData[0]);
                        int libId = Integer.parseInt(reqData[1]);
                        int bookCode = Integer.parseInt(reqData[2]);
                        RequestType type = RequestType.valueOf(reqData[3]);

                        Book b = library.searchBook(bookCode);
                        Student s = library.getStudents().get(stdNumber);
                        Librarian l = null;
                        for (Librarian lib : library.getLibrarians()) {
                            if (lib.getId() == libId) {
                                l = lib;
                                break;
                            }
                        }
                        if (b != null && s != null && l != null) {
                            Request request = new Request(b, s, l, type);
                            library.addRequest(request);
                        }
                        break;
                    case "Borrows":
                        String[] borrowData = line.split(",");
                        Student std = library.getStudents().get(Integer.parseInt(borrowData[0]));
                        Book bk = library.getBooks().get(Integer.parseInt(borrowData[1]));

                        Librarian borrower = null;
                        Librarian returner = null;

                        for (Librarian lib : library.getLibrarians()) {
                            if (lib.getId() == Integer.parseInt(borrowData[4])) borrower = lib;
                            if (borrowData.length > 5 && !borrowData[5].isEmpty() &&
                                    lib.getId() == Integer.parseInt(borrowData[5])) returner = lib;
                        }
                        if (std != null && bk != null && borrower != null) {
                            Borrow borrow = new Borrow(bk, std, LocalDate.parse(borrowData[2]), borrower);
                            if (!borrowData[3].isEmpty()) {
                                borrow.setReturnDate(LocalDate.parse(borrowData[3]));
                            }
                            if (returner != null) {
                                borrow.setReturner(returner);
                            }
                            library.addBorrow(borrow);
                        }
                        break;
                    case "BorrowRecords":
                        String[] borrowRecData = line.split(",");
                        Student std2 = library.getStudents().get(Integer.parseInt(borrowRecData[0]));
                        Book bk2 = library.getBooks().get(Integer.parseInt(borrowRecData[1]));
                        Librarian brw2 = null;
                        Librarian rtn2 = null;

                        for (Librarian lib : library.getLibrarians()) {
                            if (lib.getId() == Integer.parseInt(borrowRecData[4])) brw2 = lib;
                            if (borrowRecData.length > 5 && !borrowRecData[5].isEmpty() &&
                                    lib.getId() == Integer.parseInt(borrowRecData[5])) rtn2 = lib;
                        }

                        if (std2 != null && bk2 != null && brw2 != null) {
                            Borrow borrow = new Borrow(bk2, std2, LocalDate.parse(borrowRecData[2]), brw2);
                            if (!borrowRecData[3].isEmpty()) {
                                borrow.setReturnDate(LocalDate.parse(borrowRecData[3]));
                            }
                            if (rtn2 != null) {
                                borrow.setReturner(rtn2);
                            }
                            library.addBorrowRecord(borrow);
                        }
                        break;

                    case "DelayedReturns":
                        String[] delayData = line.split(",");
                        Student stdd = library.getStudents().get(Integer.parseInt(delayData[0]));
                        Book bkk = library.getBooks().get(Integer.parseInt(delayData[1]));
                        Librarian borrowerr = null;
                        Librarian returnerr = null;

                        for (Librarian lib : library.getLibrarians()) {
                            if (lib.getId() == Integer.parseInt(delayData[4])) borrowerr = lib;
                            if (lib.getId() == Integer.parseInt(delayData[5])) returnerr = lib;
                        }

                        if (stdd != null && bkk != null && borrowerr != null && returnerr != null) {
                            Borrow borrow = new Borrow(bkk, stdd, LocalDate.parse(delayData[2]), borrowerr);
                            borrow.setReturnDate(LocalDate.parse(delayData[3]));
                            borrow.setReturner(returnerr);
                            library.addDelayedReturn(borrow);
                        }
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found.");
        }
    }
}

