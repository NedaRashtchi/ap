package ap.Project.store;

import ap.Project.Borrow;
import ap.Project.Library;
import ap.Project.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
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
                return line.split("=")[1].trim();
            }
        }
        return "tabsplit";
    }

    public void saveLibraryData(Library library) {
        storageStrategy.save(library);
    }

    public void loadLibraryData(Library library) {
        storageStrategy.load(library);
        repairRelationships(library);
    }

    private void repairRelationships(Library library) {

        for (Borrow borrow : library.getBorrows()) {
            Student student = library.searchStudent(borrow.getStudent().getStdNumber());
            if (student != null) {
                student.addBorrowedBook(borrow.getBook());
            }
        }
        for (Borrow record : library.getBorrowedRecords()) {
            Student student = library.searchStudent(record.getStudent().getStdNumber());
            if (student != null) {
                student.addBorrowedBook(record.getBook());
            }
        }
        for (Borrow delayed : library.getDelayedReturns()) {
            Student student = library.searchStudent(delayed.getStudent().getStdNumber());
            if (student != null) {
                student.addBorrowedBook(delayed.getBook());
            }
        }
    }
}