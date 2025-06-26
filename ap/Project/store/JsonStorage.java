package ap.Project.store;

import ap.Project.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class JsonStorage implements DataStorageStrategy {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .setPrettyPrinting()
            .create();

    @Override
    public void save(Library library) {
        try {
            saveToFile("books.json", new ArrayList<>(library.getBooks().values()));
            saveToFile("students.json", new ArrayList<>(library.getStudents().values()));
            saveToFile("librarians.json", library.getLibrarians());
            saveToFile("manager.json", library.getManager());
            saveToFile("requests.json", library.getRequests());
            saveToFile("borrows.json", library.getBorrows());
            saveToFile("borrowedRecords.json", library.getBorrowedRecords());
            saveToFile("delayedReturns.json", library.getDelayedReturns());
        } catch (IOException e) {
            System.err.println("Error saving data to JSON: " + e.getMessage());
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
            loadBorrowedRecords(library);
            loadDelayedReturns(library);
        } catch (IOException e) {
            System.err.println("Error loading data from JSON: " + e.getMessage());
        }
    }

    private <T> void saveToFile(String fileName, T data) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(data, writer);
        }
    }

    private void loadBooks(Library library) throws IOException {
        if (!Files.exists(Paths.get("books.json"))) return;
        Type bookListType = new TypeToken<ArrayList<Book>>(){}.getType();
        List<Book> books = gson.fromJson(new FileReader("books.json"), bookListType);
        if (books != null) {
            for (Book book : books) {
                library.addBook(book);
            }
        }
    }

    private void loadStudents(Library library) throws IOException {
        if (!Files.exists(Paths.get("students.json"))) return;
        Type studentListType = new TypeToken<ArrayList<Student>>(){}.getType();
        List<Student> students = gson.fromJson(new FileReader("students.json"), studentListType);
        if (students != null) {
            for (Student student : students) {
                library.addStudent(student);
            }
        }
    }

    private void loadLibrarians(Library library) throws IOException {
        if (!Files.exists(Paths.get("librarians.json"))) return;
        Type librarianListType = new TypeToken<ArrayList<Librarian>>(){}.getType();
        List<Librarian> librarians = gson.fromJson(new FileReader("librarians.json"), librarianListType);
        if (librarians != null) {
            for (Librarian librarian : librarians) {
                library.addLibrarian(librarian);
            }
        }
    }

    private void loadManager(Library library) throws IOException {
        if (!Files.exists(Paths.get("manager.json"))) return;
        Manager manager = gson.fromJson(new FileReader("manager.json"), Manager.class);
        if (manager != null) {
            library.setManager(manager);
        }
    }

    private void loadRequests(Library library) throws IOException {
        if (!Files.exists(Paths.get("requests.json"))) return;
        Type requestListType = new TypeToken<ArrayList<Request>>(){}.getType();
        List<Request> requests = gson.fromJson(new FileReader("requests.json"), requestListType);
        if (requests != null) {
            for (Request request : requests) {
                library.addRequest(request);
            }
        }
    }

    private void loadBorrows(Library library) throws IOException {
        if (!Files.exists(Paths.get("borrows.json"))) return;
        Type borrowListType = new TypeToken<ArrayList<Borrow>>(){}.getType();
        List<Borrow> borrows = gson.fromJson(new FileReader("borrows.json"), borrowListType);
        if (borrows != null) {
            for (Borrow borrow : borrows) {
                library.addBorrow(borrow);

                // Link to student if it's an active borrow
                if (borrow.getReturnDate() == null) {
                    Student student = library.searchStudent(borrow.getStudent().getStdNumber());
                    if (student != null) {
                        student.addBorrowedBook(borrow.getBook());
                    }
                }
            }
        }
    }

    private void loadBorrowedRecords(Library library) throws IOException {
        if (!Files.exists(Paths.get("borrowedRecords.json"))) return;
        Type borrowListType = new TypeToken<ArrayList<Borrow>>(){}.getType();
        List<Borrow> records = gson.fromJson(new FileReader("borrowedRecords.json"), borrowListType);
        if (records != null) {
            for (Borrow record : records) {
                library.addBorrowRecord(record);
            }
        }
    }
    private void loadDelayedReturns(Library library) throws IOException {
        if (!Files.exists(Paths.get("delayedReturns.json"))) return;
        Type borrowListType = new TypeToken<ArrayList<Borrow>>(){}.getType();
        List<Borrow> delayed = gson.fromJson(new FileReader("delayedReturns.json"), borrowListType);
        if (delayed != null) {
            for (Borrow d : delayed) {
                library.addDelayedReturn(d);
            }
        }
    }
}