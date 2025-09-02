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
            Type loanListType = new TypeToken<List<SimplifiedLoan>>(){}.getType();
            List<SimplifiedLoan> simplifiedLoans = SimplifiedLoan.fromLoans(system.getLoanManager().getLoans());
            gson.toJson(simplifiedLoans, writer);
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
            Type simplifiedLoanListType = new TypeToken<List<SimplifiedLoan>>(){}.getType();
            List<SimplifiedLoan> simplifiedLoans = gson.fromJson(reader, simplifiedLoanListType);

            if (simplifiedLoans != null) {
                for (SimplifiedLoan simplifiedLoan : simplifiedLoans) {
                    Student student = system.getStudentManager().getStudents().stream()
                            .filter(s -> s.getUsername().equals(simplifiedLoan.studentUsername))
                            .findFirst()
                            .orElse(null);

                    Book book = system.getBookManager().getBooks().stream()
                            .filter(b -> b.getBookCode() == simplifiedLoan.bookCode)
                            .findFirst()
                            .orElse(null);

                    if (student != null && book != null) {
                        Loan loan = new Loan(
                                student,
                                book,
                                simplifiedLoan.requestDate,
                                simplifiedLoan.borrowDate,
                                simplifiedLoan.returnDate,
                                simplifiedLoan.status
                        );
                        system.getLoanManager().getLoans().add(loan);

                        if (simplifiedLoan.status == LoanStatus.BORROWED) {
                            book.setStatus("Borrowed");
                        }
                    }
                }
            }
        } catch (IOException e) {

        }
    }

    private static class SimplifiedLoan {
        String studentUsername;
        int bookCode;
        LocalDate requestDate;
        LocalDate borrowDate;
        LocalDate returnDate;
        LoanStatus status;

        public SimplifiedLoan(String studentUsername, int bookCode, LocalDate requestDate,
                              LocalDate borrowDate, LocalDate returnDate, LoanStatus status) {
            this.studentUsername = studentUsername;
            this.bookCode = bookCode;
            this.requestDate = requestDate;
            this.borrowDate = borrowDate;
            this.returnDate = returnDate;
            this.status = status;
        }

        public static List<SimplifiedLoan> fromLoans(List<Loan> loans) {
            return loans.stream()
                    .map(loan -> new SimplifiedLoan(
                            loan.getStudent().getUsername(),
                            loan.getBook().getBookCode(),
                            loan.getRequestDate(),
                            loan.getBorrowDate(),
                            loan.getReturnDate(),
                            loan.getStatus()
                    ))
                    .collect(java.util.stream.Collectors.toList());
        }
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