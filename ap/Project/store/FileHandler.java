package ap.Project.store;

import ap.Project.Book;
import ap.Project.Borrow;
import ap.Project.Librarian;
import ap.Project.Library;
import ap.Project.Request;
import ap.Project.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private DataStorageStrategy storageStrategy;

    public FileHandler() throws IOException, SQLException {
        String storageType = readConfig();
        switch (storageType) {
            case "tabsplit":
                storageStrategy = new TabSplitStorage();
                break;
            case "json":
                storageStrategy = new JsonStorage();
                break;
            case "sqlite":
                storageStrategy = new SQLiteStorage();
                break;
            default:
                throw new IllegalArgumentException("Unsupported storage type: " + storageType);
        }
    }
    private String readConfig() throws IOException {
        if (!Files.exists(Paths.get("config.txt"))) {
            System.out.println("Config file not found, using default: tabsplit");
            return "tabsplit";
        }
        List<String> lines = Files.readAllLines(Paths.get("config.txt"));
        for (String line : lines) {
            if (line.startsWith("storage=")) {
                String value = line.substring("storage=".length()).trim();
                if (!value.isEmpty()) {
                    return value;
                }
            }
        }
        return "tabsplit";
    }

    public void saveLibraryData(Library library) {
        storageStrategy.save(library);
    }

    public void loadLibraryData(Library library) {
        storageStrategy.load(library);
        reconcileRelationships(library);
    }

    private void reconcileRelationships(Library library) {
        // Deserialized objects are detached copies; point every request/borrow
        // at the real instances owned by the library so approvals and
        // comparisons affect the actual data.
        for (Request request : library.getRequests()) {
            Book book = request.getBook() != null ? library.searchBook(request.getBook().getBookCode()) : null;
            Student student = request.getStudent() != null ? library.searchStudent(request.getStudent().getStdNumber()) : null;
            Librarian librarian = request.getLibrarian() != null ? library.findLibrarianById(request.getLibrarian().getId()) : null;
            if (book != null) request.setBook(book);
            if (student != null) request.setStudent(student);
            if (librarian != null) request.setLibrarian(librarian);
        }
        List<Borrow> allBorrows = new ArrayList<>();
        allBorrows.addAll(library.getBorrows());
        allBorrows.addAll(library.getBorrowedRecords());
        allBorrows.addAll(library.getDelayedReturns());
        for (Borrow borrow : allBorrows) {
            Book book = borrow.getBook() != null ? library.searchBook(borrow.getBook().getBookCode()) : null;
            Student student = borrow.getStudent() != null ? library.searchStudent(borrow.getStudent().getStdNumber()) : null;
            Librarian borrower = borrow.getBorrower() != null ? library.findLibrarianById(borrow.getBorrower().getId()) : null;
            Librarian returner = borrow.getReturner() != null ? library.findLibrarianById(borrow.getReturner().getId()) : null;
            if (book != null) borrow.setBook(book);
            if (student != null) borrow.setStudent(student);
            if (borrower != null) borrow.setBorrower(borrower);
            if (returner != null) borrow.setReturner(returner);
        }

        // A student's unreturned books come only from active borrows;
        // returned and delayed records must not resurrect as borrowed.
        for (Student student : library.getStudents().values()) {
            student.getBorrowedBooks().clear();
        }
        for (Borrow borrow : library.getBorrows()) {
            Student student = library.searchStudent(borrow.getStudent().getStdNumber());
            if (student != null) {
                student.addBorrowedBook(borrow.getBook());
            }
        }
    }
}