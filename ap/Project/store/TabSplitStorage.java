package ap.Project.store;

import ap.Project.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class TabSplitStorage implements DataStorageStrategy {
    @Override
    public void save(Library library) {
        try {
            saveBooks(library);
            saveStudents(library);
            saveLibrarians(library);
            saveManager(library);
            saveRequests(library);
            saveBorrows(library);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
    @Override
    public void load(Library library) {
        try {
            loadBooks(library);
            loadStudents(library);
            loadLibrarians(library);
            loadManager(library);
            loadRequests(library);
            loadBorrows(library);
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
    private void saveBooks(Library library) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("books.tsv"))) {
            for (Book book : library.getBooks().values()) {
                writer.println(book.getTitle() + "\t" +
                        book.getAuthor() + "\t" +
                        book.getPublicationYear() + "\t" +
                        book.getPageCount() + "\t" +
                        book.getBookCode());
            }
        }
    }
    private void loadBooks(Library library) throws IOException {
        if (!Files.exists(Paths.get("books.tsv"))) return;
        List<String> lines = Files.readAllLines(Paths.get("books.tsv"));
        for (String line : lines) {
            String[] parts = line.split("\t");
            Book book = new Book(parts[0], parts[1],
                    Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[3]),
                    Integer.parseInt(parts[4]));
            library.addBook(book);
        }
    }
    private void saveStudents(Library library) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("students.tsv"))) {
            for (Student student : library.getStudents().values()) {
                writer.println(student.getStdNumber() + "\t" +
                        student.getName() + "\t" +
                        student.getMajor() + "\t" +
                        student.getRegisterDate());
            }
        }
    }
    private void loadStudents(Library library) throws IOException {
        if (!Files.exists(Paths.get("students.tsv"))) return;
        List<String> lines = Files.readAllLines(Paths.get("students.tsv"));
        for (String line : lines) {
            String[] parts = line.split("\t");
            String[] nameParts = parts[1].split(" ");
            Student student = new Student(nameParts[0], nameParts[1],
                    Integer.parseInt(parts[0]), parts[2]);
            student.setRegisterDate(LocalDate.parse(parts[3]));
            library.addStudent(student);
        }
    }
    private void saveLibrarians(Library library) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("librarians.tsv"))) {
            for (Librarian librarian : library.getLibrarians()) {
                writer.println(librarian.getId() + "\t" +
                        librarian.getName() + "\t" +
                        librarian.getRegisterDate() + "\t" +
                        librarian.getBorrowCount() + "\t" +
                        librarian.getReturnCount());
            }
        }
    }
    private void loadLibrarians(Library library) throws IOException {
        if (!Files.exists(Paths.get("librarians.tsv"))) return;
        List<String> lines = Files.readAllLines(Paths.get("librarians.tsv"));
        for (String line : lines) {
            String[] parts = line.split("\t");
            String[] nameParts = parts[1].split(" ");
            Librarian librarian = new Librarian(nameParts[0], nameParts[1],
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[3]),
                    Integer.parseInt(parts[4]));
            librarian.setRegisterDate(LocalDate.parse(parts[2]));
            library.addLibrarian(librarian);
        }
    }
    private void saveManager(Library library) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("manager.tsv"))) {
            Manager manager = library.getManager();
            writer.println(manager.getId() + "\t" +
                    manager.getName() + "\t" +
                    manager.getEducation());
        }
    }
    private void loadManager(Library library) throws IOException {
        if (!Files.exists(Paths.get("manager.tsv"))) return;
        List<String> lines = Files.readAllLines(Paths.get("manager.tsv"));
        if (lines.isEmpty()) return;
        String[] parts = lines.get(0).split("\t");
        String[] nameParts = parts[1].split(" ");
        Manager manager = new Manager(nameParts[0], nameParts[1],
                Education.valueOf(parts[2]), Integer.parseInt(parts[0]));
        library.setManager(manager);
    }
    private void saveRequests(Library library) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("requests.tsv"))) {
            for (Request request : library.getRequests()) {
                writer.println(request.getStudent().getStdNumber() + "\t" +
                        request.getLibrarian().getId() + "\t" +
                        request.getBook().getBookCode() + "\t" +
                        request.getType());
            }
        }
    }
    private void loadRequests(Library library) throws IOException {
        if (!Files.exists(Paths.get("requests.tsv"))) return;
        List<String> lines = Files.readAllLines(Paths.get("requests.tsv"));
        for (String line : lines) {
            String[] parts = line.split("\t");
            int stdNumber = Integer.parseInt(parts[0]);
            int libId = Integer.parseInt(parts[1]);
            int bookCode = Integer.parseInt(parts[2]);
            RequestType type = RequestType.valueOf(parts[3]);

            Book book = library.searchBook(bookCode);
            Student student = library.searchStudent(stdNumber);
            Librarian librarian = null;
            for (Librarian l : library.getLibrarians()) {
                if (l.getId() == libId) {
                    librarian = l;
                    break;
                }
            }

            if (book != null && student != null && librarian != null) {
                library.addRequest(new Request(book, student, librarian, type));
            }
        }
    }
    private void saveBorrows(Library library) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("borrows.tsv"))) {
            for (Borrow borrow : library.getBorrows()) {
                writer.println(borrow.getStudent().getStdNumber() + "\t" +
                                borrow.getBook().getBookCode() + "\t" +
                                borrow.getBorrowDate() + "\t" +
                                (borrow.getReturnDate() != null ? borrow.getReturnDate() : "") + "\t" +
                                borrow.getBorrower().getId() + "\t" +
                                (borrow.getReturner() != null ? borrow.getReturner().getId() : "null")
                );
            }
        }
    }
    private void loadBorrows(Library library) throws IOException {
        if (!Files.exists(Paths.get("borrows.tsv"))) return;
        List<String> lines = Files.readAllLines(Paths.get("borrows.tsv"));
        for (String line : lines) {
            String[] parts = line.split("\t");
            if (parts.length < 6) continue;

            int stdNumber = Integer.parseInt(parts[0]);
            int bookCode = Integer.parseInt(parts[1]);
            LocalDate borrowDate = LocalDate.parse(parts[2]);
            LocalDate returnDate = !parts[3].isEmpty() ? LocalDate.parse(parts[3]) : null;
            int borrowerId = Integer.parseInt(parts[4]);
            int returnerId = parts[5].equals("null") ? -1 : Integer.parseInt(parts[5]);

            Student student = library.searchStudent(stdNumber);
            Book book = library.searchBook(bookCode);
            Librarian borrower = findLibrarianById(library, borrowerId);
            Librarian returner = returnerId != -1 ? findLibrarianById(library, returnerId) : null;

            if (student != null && book != null && borrower != null) {
                Borrow borrow = new Borrow(book, student, borrowDate, borrower);
                if (returnDate != null) {
                    borrow.setReturnDate(returnDate);
                }
                if (returner != null) {
                    borrow.setReturner(returner);
                }
                library.addBorrow(borrow);

                if (returnDate == null) {
                    student.addBorrowedBook(book);
                }
            }
        }
    }

    private Librarian findLibrarianById(Library library, int id) {
        for (Librarian l : library.getLibrarians()) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }
}