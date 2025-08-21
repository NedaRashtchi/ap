package ap.finalproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileHandler {
    private static final String STUDENTS_FILE = "students.json";
    private static final String LIBRARIANS_FILE = "librarians.json";
    private static final String MANAGER_FILE = "manager.json";
    private static final String BOOKS_FILE = "books.json";
    private static final String LOANS_FILE = "loans.json";

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .create();

    public static void saveData(LibrarySystem system) {
        try (FileWriter writer = new FileWriter(STUDENTS_FILE)) {
            gson.toJson(system.getStudentManager().getStudents(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(LIBRARIANS_FILE)) {
            gson.toJson(system.getLibrarianManager().getLibrarians(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(MANAGER_FILE)) {
            gson.toJson(system.getManager(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(BOOKS_FILE)) {
            gson.toJson(system.getBookManager().getBooks(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(LOANS_FILE)) {
            gson.toJson(system.getLoanManager().getLoans(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        try (FileReader reader = new FileReader(LOANS_FILE)) {
            Type loanListType = new TypeToken<List<Loan>>(){}.getType();
            List<Loan> loans = gson.fromJson(reader, loanListType);
            if (loans != null) {
                system.getLoanManager().setLoans(loans);
            }
        } catch (IOException e) {}
    }

    private static class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public void write(JsonWriter out, LocalDate date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(formatter.format(date));
            }
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            String dateString = in.nextString();
            return LocalDate.parse(dateString, formatter);
        }
    }
}