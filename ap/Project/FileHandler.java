package ap.Project;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class FileHandler {
    public void saveLibraryData(Library library) {
        saveBook(library);
        saveStudent(library);
        saveLibrarian(library);
        saveManager(library);
        saveRequest(library);
        saveBorrows(library);
        saveBorrowRecords(library);
        saveDelayedReturns(library);
    }
    public void loadLibraryData(Library library) {
        loadBook(library);
        loadStudent(library);
        loadLibrarian(library);
        loadManager(library);
        loadRequest(library);
        loadBorrows(library);
        loadBorrowRecords(library);
        loadDelayedReturns(library);
    }
    private Scanner getScanner(String filename) {
        try {
            return new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename + ": " + e.getMessage());
            return null;
        }
    }
    private PrintWriter getWriter(String filename) {
        try {
            return new PrintWriter(new FileWriter(filename));
        } catch (IOException e) {
            System.out.println("Error creating file writer: " + filename + ": " + e.getMessage());
            return null;
        }
    }
    public void saveBook(Library library) {
        try (PrintWriter writer = getWriter("book_data.txt")) {
            for (Book book : library.getBooks().values()) {
                assert writer != null; // println may produce nullPointerException
                writer.println(book.getTitle() + "," + book.getAuthor() + "," +
                        book.getPublicationYear() + "," + book.getPageCount() + "," + book.getBookCode());
            }
        } catch (Exception e) {
            System.out.println("Error saving book data" + e.getMessage());
        }
    }

    public void loadBook(Library library) {
        try (Scanner fileScanner = getScanner("book_data.txt")) {
            while (true) {
                assert fileScanner != null; // hasNextLine may produce nullPointerException
                if (!fileScanner.hasNextLine()) break;
                String line = fileScanner.nextLine();
                String[] bookData = line.split(",");
                Book book = new Book(bookData[0], bookData[1], Integer.parseInt(bookData[2]),
                        Integer.parseInt(bookData[3]), Integer.parseInt(bookData[4]));

                library.addBook(book);
            }
        } catch (Exception e) {
            System.out.println("Error loading book data" + e.getMessage());
        }
    }

    public void saveStudent(Library library) {
        try (PrintWriter writer = getWriter("student_data.txt")) {
            for (Student student : library.getStudents().values()) {
                StringBuilder borrowedCodes = new StringBuilder();

                for (int i = 0; i < student.getBorrowedBooks().size(); i++) {
                    Book book = student.getBorrowedBooks().get(i);
                    borrowedCodes.append(book.getBookCode());

                    if (i < student.getBorrowedBooks().size() - 1) {
                        borrowedCodes.append(":");
                    }
                }
                assert writer != null;
                writer.println(student.getStdNumber() + "," + student.getName() + "," +
                        student.getMajor() + "," + student.getRegisterDate() + "," + borrowedCodes);
            }
        } catch (Exception e) {
            System.out.println("Error saving student data" + e.getMessage());
        }
    }
    public void loadStudent(Library library) {
        try (Scanner scanner = getScanner("student_data.txt")) {
            while (scanner != null && scanner.hasNextLine()) {
                String line = scanner.nextLine();
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
            }
        } catch (Exception e) {
            System.out.println("Error loading student data" + e.getMessage());
        }
    }
    public void saveLibrarian(Library library) {
        try (PrintWriter writer = getWriter("librarian_data.txt")) {
            for (Librarian librarian : library.getLibrarians()) {
                assert writer != null;
                writer.println(librarian.getId() + "," + librarian.getName() + "," +
                        librarian.getRegisterDate() + "," + librarian.getBorrowCount() + "," +
                        librarian.getReturnCount());
            }
        } catch (Exception e) {
            System.out.println("Error saving librarian data" + e.getMessage());
        }
    }
    public void loadLibrarian(Library library) {
        try (Scanner scanner = getScanner("librarian_data.txt")) {
            while (scanner != null && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] libData = line.split(",");
                Librarian librarian = new Librarian(libData[1].split(" ")[0],
                        libData[1].split(" ")[1], Integer.parseInt(libData[0]),
                        Integer.parseInt(libData[3]), Integer.parseInt(libData[4]));
                librarian.setRegisterDate(LocalDate.parse(libData[2]));

                library.addLibrarian(librarian);
            }
        } catch (Exception e) {
            System.out.println("Error loading librarian data" + e.getMessage());
        }
    }
    public void saveManager(Library library) {
        try (PrintWriter writer = getWriter("manager_data.txt")) {
            Manager manager = library.getManager();
            assert writer != null;
            writer.println(manager.getId() + "," + manager.getName() + "," + manager.getEducation());
        } catch (Exception e) {
            System.out.println("Error saving manager data" + e.getMessage());
        }
    }
    public void loadManager(Library library) {
        try (Scanner scanner = getScanner("manager_data.txt")) {
            while (scanner != null && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] mgrData = line.split(",");
                Manager manager = new Manager(mgrData[1].split(" ")[0],
                        mgrData[1].split(" ")[1], Education.valueOf(mgrData[2]), Integer.parseInt(mgrData[0]));

                library.setManager(manager);
            }
        } catch (Exception e) {
            System.out.println("Error loading manager data" + e.getMessage());
        }
    }
    public void saveRequest(Library library) {
        try (PrintWriter writer = getWriter("request_data.txt")) {
            for (Request request : library.getRequests()) {
                int stdNumber = request.getStudent() != null ? request.getStudent().getStdNumber() : -1;
                int libId = request.getLibrarian() != null ? request.getLibrarian().getId() : -1;
                int bookCode = request.getBook() != null ? request.getBook().getBookCode() : -1;

                assert writer != null;
                writer.println(stdNumber + "," + libId + "," + bookCode + "," + request.getType());
            }
        } catch (Exception e) {
            System.out.println("Error saving request data" + e.getMessage());
        }
    }
    public void loadRequest(Library library) {
        try (Scanner scanner = getScanner("request_data.txt")) {
            while (scanner != null && scanner.hasNextLine()) {
                String line = scanner.nextLine();
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
            }
        } catch (Exception e) {
            System.out.println("Error loading manager data" + e.getMessage());
        }
    }
    public void saveBorrows(Library library) {
        try (PrintWriter writer = getWriter("borrow_data.txt")) {
            for (Borrow borrow : library.getBorrows()) {
                assert writer != null;
                writer.println(borrow.getStudent().getStdNumber() + "," + borrow.getBook().getBookCode() + "," + borrow.getBorrowDate() + "," +
                        (borrow.getReturnDate() != null ? borrow.getReturnDate() : "") + "," + borrow.getBorrower().getId() + "," +
                        (borrow.getReturner() != null ? borrow.getReturner().getId() : ""));
            }
        } catch (Exception e) {
            System.out.println("Error saving borrow data" + e.getMessage());
        }
    }

    public void loadBorrows(Library library) {
        try (Scanner scanner = getScanner("borrow_data.txt")) {
            while (scanner != null && scanner.hasNextLine()) {
                String line = scanner.nextLine();
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
            }
        } catch (Exception e) {
            System.out.println("Error loading borrow data" + e.getMessage());
        }
    }
    public void saveBorrowRecords(Library library) {
        try (PrintWriter writer = getWriter("borrowrecord_data.txt")) {
            for (Borrow borrow : library.getBorrowedRecords()) {
                assert writer != null;
                writer.println(borrow.getStudent().getStdNumber() + "," + borrow.getBook().getBookCode() + "," +
                        borrow.getBorrowDate() + "," + (borrow.getReturnDate() != null ? borrow.getReturnDate() : "") + "," +
                        borrow.getBorrower().getId() + "," + (borrow.getReturner() != null ? borrow.getReturner().getId() : ""));
            }
        } catch (Exception e) {
            System.out.println("Error saving borrow record data" + e.getMessage());
        }
    }
    public void loadBorrowRecords(Library library) {
        try (Scanner scanner = getScanner("borrow_data.txt")) {
            while (scanner != null && scanner.hasNextLine()) {
                String line = scanner.nextLine();

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
            }
        } catch (Exception e) {
            System.out.println("Error loading borrow data" + e.getMessage());
        }
    }
    public void saveDelayedReturns(Library library) {
        try (PrintWriter writer = getWriter("delayedreturns_data.txt")) {
            for (Borrow borrow : library.getDelayedReturns()) {
                assert writer != null;
                writer.println(borrow.getStudent().getStdNumber() + "," + borrow.getBook().getBookCode() + "," +
                        borrow.getBorrowDate() + "," + borrow.getReturnDate() + "," +
                        borrow.getBorrower().getId() + "," + borrow.getReturner().getId());
            }
        } catch (Exception e) {
            System.out.println("Error saving delayed returns data" + e.getMessage());
        }
    }
    public void loadDelayedReturns(Library library) {
        try (Scanner scanner = getScanner("delayedreturns_data.txt")) {
            while (scanner != null && scanner.hasNextLine()) {
                String line = scanner.nextLine();
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
            }
        } catch (Exception e) {
            System.out.println("Error loading delayed returns data" + e.getMessage());
        }
    }
}