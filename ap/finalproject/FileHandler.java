package ap.finalproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class FileHandler {
    private static final String STUDENTS_FILE = "students.json";
    private static final String LIBRARIANS_FILE = "librarians.json";
    private static final String MANAGER_FILE = "manager.json";
    private static final String BOOKS_FILE = "books.json";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveData(LibrarySystem system) {
        try (FileWriter writer = new FileWriter(STUDENTS_FILE)) {
            gson.toJson(system.getStudentManager().getStudents(), writer);
        } catch (IOException e) {}

        try (FileWriter writer = new FileWriter(LIBRARIANS_FILE)) {
            gson.toJson(system.getLibrarianManager().getLibrarians(), writer);
        } catch (IOException e) {}

        try (FileWriter writer = new FileWriter(MANAGER_FILE)) {
            gson.toJson(system.getManager(), writer);
        } catch (IOException e) {}

        try (FileWriter writer = new FileWriter(BOOKS_FILE)) {
            gson.toJson(system.getBookManager().getBooks(), writer);
        } catch (IOException e) {}
    }

    public static void loadData(LibrarySystem system) {
        try (FileReader reader = new FileReader(STUDENTS_FILE)) {
            Type studentListType = new TypeToken<List<Student>>(){}.getType();
            List<Student> students = gson.fromJson(reader, studentListType);
            if (students != null) {
                system.getStudentManager().setStudents(students);
            }
        } catch (IOException e) {}

        try (FileReader reader = new FileReader(LIBRARIANS_FILE)) {
            Type librarianListType = new TypeToken<List<Librarian>>(){}.getType();
            List<Librarian> librarians = gson.fromJson(reader, librarianListType);
            if (librarians != null) {
                system.getLibrarianManager().setLibrarians(librarians);
            }
        } catch (IOException e) {}

        try (FileReader reader = new FileReader(MANAGER_FILE)) {
            Manager manager = gson.fromJson(reader, Manager.class);
            if (manager != null) {
                system.setManager(manager);
            }
        } catch (IOException e) {}

        try (FileReader reader = new FileReader(BOOKS_FILE)) {
            Type bookListType = new TypeToken<List<Book>>(){}.getType();
            List<Book> books = gson.fromJson(reader, bookListType);
            if (books != null) {
                system.getBookManager().setBooks(books);
            }
        } catch (IOException e) {}
    }
}