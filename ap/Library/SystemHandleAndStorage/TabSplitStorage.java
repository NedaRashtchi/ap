package ap.Library.SystemHandleAndStorage;

import ap.Library.Book;
import ap.Library.Librarian;
import ap.Library.Library;
import ap.Library.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class TabSplitStorage implements DataStorageStrategy {
    @Override
    public void save(Library library) {
        try {
//            saveBooks(library);
            saveStudents(library);
            saveLibrarians(library);
//            saveManager(library);
//            saveRequests(library);
//            saveBorrows(library);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
    @Override
    public void load(Library library) {
        try {
//            loadBooks(library);
            loadStudents(library);
//            loadLibrarians(library);
//            loadManager(library);
//            loadRequests(library);
//            loadBorrows(library);
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
//    private void saveBooks(Library library) throws IOException {
//        try (PrintWriter writer = new PrintWriter(new FileWriter("books.tsv"))) {
//            for (Book book : library.getBooks().values()) {
//                writer.println(book.getTitle() + "\t" +
//                        book.getAuthor() + "\t" +
//                        book.getPublicationYear() + "\t" +
//                        book.getPageCount() + "\t" +
//                        book.getBookCode());
//            }
//        }
//    }
//    private void loadBooks(Library library) throws IOException {
//        if (!Files.exists(Paths.get("books.tsv"))) return;
//        List<String> lines = Files.readAllLines(Paths.get("books.tsv"));
//        for (String line : lines) {
//            String[] parts = line.split("\t");
//            Book book = new Book(parts[0], parts[1],
//                    Integer.parseInt(parts[2]),
//                    Integer.parseInt(parts[3]),
//                    Integer.parseInt(parts[4]));
//            library.addBook(book);
//        }
//    }
    private void saveStudents(Library library) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("students.tsv"))) {
            for (Student student : library.getStudents().values()) {
                writer.println(student.getName() + "\t" +
                        student.getIdNumber() + "\t" +
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
            for (Librarian librarian : library.getLibrarians().values()) {
                writer.println(librarian.getIdNumber() + "\t" +
                        librarian.getName() + "\t" +
                        librarian.getRegisterDate() + "\t" +
                        librarian.getBorrowCount() + "\t" +
                        librarian.getReturnCount());
            }
        }
    }
//    private void loadLibrarians(Library library) throws IOException {
//        if (!Files.exists(Paths.get("librarians.tsv"))) return;
//        List<String> lines = Files.readAllLines(Paths.get("librarians.tsv"));
//        for (String line : lines) {
//            String[] parts = line.split("\t");
//            String[] nameParts = parts[1].split(" ");
//            Librarian librarian = new Librarian(nameParts[0], nameParts[1],
//                    Integer.parseInt(parts[0]),
//                    Integer.parseInt(parts[3]),
//                    Integer.parseInt(parts[4]));
//            librarian.setRegisterDate(LocalDate.parse(parts[2]));
//            library.addLibrarian(librarian);
//        }
//    }
}
