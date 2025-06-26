package ap.Project.store;

import ap.Project.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SQLiteStorage implements DataStorageStrategy {
    private Connection connection;

    public SQLiteStorage() throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:library.db");
            createTables();
        } catch (SQLException e) {
            System.err.println("Error connecting to SQLite database: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(Library library) {
        try {
            clearTables();
            saveBooks(library);
            saveStudents(library);
            saveLibrarians(library);
            saveManager(library);
            saveRequests(library);
            saveBorrows(library, "borrows");
            saveBorrows(library, "borrowed_records");
            saveBorrows(library, "delayed_returns");
        } catch (SQLException e) {
            System.err.println("Error saving data to SQLite: " + e.getMessage());
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
            loadBorrows(library, "borrows");
            loadBorrows(library, "borrowed_records");
            loadBorrows(library, "delayed_returns");
        } catch (SQLException e) {
            System.err.println("Error loading data from SQLite: " + e.getMessage());
        }
    }

    private void createTables() throws SQLException {
        String[] createStatements = {
                "CREATE TABLE IF NOT EXISTS books (" +
                        "bookCode INTEGER PRIMARY KEY, " +
                        "title TEXT, " +
                        "author TEXT, " +
                        "publicationYear INTEGER, " +
                        "pageCount INTEGER)",

                "CREATE TABLE IF NOT EXISTS students (" +
                        "stdNumber INTEGER PRIMARY KEY, " +
                        "firstName TEXT, " +
                        "lastName TEXT, " +
                        "major TEXT, " +
                        "registerDate TEXT)",

                "CREATE TABLE IF NOT EXISTS librarians (" +
                        "id INTEGER PRIMARY KEY, " +
                        "firstName TEXT, " +
                        "lastName TEXT, " +
                        "registerDate TEXT, " +
                        "borrowCount INTEGER, " +
                        "returnCount INTEGER)",

                "CREATE TABLE IF NOT EXISTS manager (" +
                        "id INTEGER PRIMARY KEY, " +
                        "firstName TEXT, " +
                        "lastName TEXT, " +
                        "education TEXT)",

                "CREATE TABLE IF NOT EXISTS requests (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "studentStdNumber INTEGER, " +
                        "librarianId INTEGER, " +
                        "bookCode INTEGER, " +
                        "requestType TEXT, " +
                        "FOREIGN KEY(studentStdNumber) REFERENCES students(stdNumber), " +
                        "FOREIGN KEY(librarianId) REFERENCES librarians(id), " +
                        "FOREIGN KEY(bookCode) REFERENCES books(bookCode))",

                "CREATE TABLE IF NOT EXISTS borrows (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "studentStdNumber INTEGER, " +
                        "bookCode INTEGER, " +
                        "borrowDate TEXT, " +
                        "returnDate TEXT, " +
                        "borrowerId INTEGER, " +
                        "returnerId INTEGER, " +
                        "FOREIGN KEY(studentStdNumber) REFERENCES students(stdNumber), " +
                        "FOREIGN KEY(bookCode) REFERENCES books(bookCode), " +
                        "FOREIGN KEY(borrowerId) REFERENCES librarians(id), " +
                        "FOREIGN KEY(returnerId) REFERENCES librarians(id))",

                "CREATE TABLE IF NOT EXISTS borrowed_records (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "studentStdNumber INTEGER, " +
                        "bookCode INTEGER, " +
                        "borrowDate TEXT, " +
                        "returnDate TEXT, " +
                        "borrowerId INTEGER, " +
                        "returnerId INTEGER, " +
                        "FOREIGN KEY(studentStdNumber) REFERENCES students(stdNumber), " +
                        "FOREIGN KEY(bookCode) REFERENCES books(bookCode), " +
                        "FOREIGN KEY(borrowerId) REFERENCES librarians(id), " +
                        "FOREIGN KEY(returnerId) REFERENCES librarians(id))",

                "CREATE TABLE IF NOT EXISTS delayed_returns (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "studentStdNumber INTEGER, " +
                        "bookCode INTEGER, " +
                        "borrowDate TEXT, " +
                        "returnDate TEXT, " +
                        "borrowerId INTEGER, " +
                        "returnerId INTEGER, " +
                        "FOREIGN KEY(studentStdNumber) REFERENCES students(stdNumber), " +
                        "FOREIGN KEY(bookCode) REFERENCES books(bookCode), " +
                        "FOREIGN KEY(borrowerId) REFERENCES librarians(id), " +
                        "FOREIGN KEY(returnerId) REFERENCES librarians(id))"
        };

        try (Statement stmt = connection.createStatement()) {
            for (String sql : createStatements) {
                stmt.execute(sql);
            }
        }
    }

    private void clearTables() throws SQLException {
        String[] tables = {
                "books", "students", "librarians", "manager",
                "requests", "borrows", "borrowed_records", "delayed_returns"
        };

        try (Statement stmt = connection.createStatement()) {
            for (String table : tables) {
                stmt.execute("DELETE FROM " + table);
            }
        }
    }

    private void saveBooks(Library library) throws SQLException {
        String sql = "INSERT INTO books(bookCode, title, author, publicationYear, pageCount) VALUES(?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Book book : library.getBooks().values()) {
                pstmt.setInt(1, book.getBookCode());
                pstmt.setString(2, book.getTitle());
                pstmt.setString(3, book.getAuthor());
                pstmt.setInt(4, book.getPublicationYear());
                pstmt.setInt(5, book.getPageCount());
                pstmt.executeUpdate();
            }
        }
    }

    private void saveStudents(Library library) throws SQLException {
        String sql = "INSERT INTO students(stdNumber, firstName, lastName, major, registerDate) VALUES(?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Student student : library.getStudents().values()) {
                pstmt.setInt(1, student.getStdNumber());
                pstmt.setString(2, student.getName().split(" ")[0]);
                pstmt.setString(3, student.getName().split(" ")[1]);
                pstmt.setString(4, student.getMajor());
                pstmt.setString(5, student.getRegisterDate().toString());
                pstmt.executeUpdate();
            }
        }
    }

    private void saveLibrarians(Library library) throws SQLException {
        String sql = "INSERT INTO librarians(id, firstName, lastName, registerDate, borrowCount, returnCount) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Librarian librarian : library.getLibrarians()) {
                pstmt.setInt(1, librarian.getId());
                pstmt.setString(2, librarian.getName().split(" ")[0]);
                pstmt.setString(3, librarian.getName().split(" ")[1]);
                pstmt.setString(4, librarian.getRegisterDate().toString());
                pstmt.setInt(5, librarian.getBorrowCount());
                pstmt.setInt(6, librarian.getReturnCount());
                pstmt.executeUpdate();
            }
        }
    }

    private void saveManager(Library library) throws SQLException {
        String sql = "INSERT INTO manager(id, firstName, lastName, education) VALUES(?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            Manager manager = library.getManager();
            pstmt.setInt(1, manager.getId());
            pstmt.setString(2, manager.getName().split(" ")[0]);
            pstmt.setString(3, manager.getName().split(" ")[1]);
            pstmt.setString(4, manager.getEducation().toString());
            pstmt.executeUpdate();
        }
    }

    private void saveRequests(Library library) throws SQLException {
        String sql = "INSERT INTO requests(studentStdNumber, librarianId, bookCode, requestType) VALUES(?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Request request : library.getRequests()) {
                pstmt.setInt(1, request.getStudent().getStdNumber());
                pstmt.setInt(2, request.getLibrarian().getId());
                pstmt.setInt(3, request.getBook().getBookCode());
                pstmt.setString(4, request.getType().toString());
                pstmt.executeUpdate();
            }
        }
    }

    private void saveBorrows(Library library, String tableName) throws SQLException {
        List<Borrow> borrows = new ArrayList<>();
        if (tableName.equals("borrows")) {
            borrows = library.getBorrows();
        } else if (tableName.equals("borrowed_records")) {
            borrows = library.getBorrowedRecords();
        } else if (tableName.equals("delayed_returns")) {
            borrows = library.getDelayedReturns();
        }

        if (borrows.isEmpty()) return;

        String sql = "INSERT INTO " + tableName + "(studentStdNumber, bookCode, borrowDate, returnDate, borrowerId, returnerId) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Borrow borrow : borrows) {
                pstmt.setInt(1, borrow.getStudent().getStdNumber());
                pstmt.setInt(2, borrow.getBook().getBookCode());
                pstmt.setString(3, borrow.getBorrowDate().toString());
                pstmt.setString(4, borrow.getReturnDate() != null ? borrow.getReturnDate().toString() : null);
                pstmt.setInt(5, borrow.getBorrower().getId());
                pstmt.setObject(6, borrow.getReturner() != null ? borrow.getReturner().getId() : null);
                pstmt.executeUpdate();
            }
        }
    }

    private void loadBooks(Library library) throws SQLException {
        String sql = "SELECT * FROM books";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("publicationYear"),
                        rs.getInt("pageCount"),
                        rs.getInt("bookCode")
                );
                library.addBook(book);
            }
        }
    }

    private void loadStudents(Library library) throws SQLException {
        String sql = "SELECT * FROM students";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student student = new Student(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("stdNumber"),
                        rs.getString("major")
                );
                student.setRegisterDate(LocalDate.parse(rs.getString("registerDate")));
                library.addStudent(student);
            }
        }
    }

    private void loadLibrarians(Library library) throws SQLException {
        String sql = "SELECT * FROM librarians";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Librarian librarian = new Librarian(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("id"),
                        rs.getInt("borrowCount"),
                        rs.getInt("returnCount")
                );
                librarian.setRegisterDate(LocalDate.parse(rs.getString("registerDate")));
                library.addLibrarian(librarian);
            }
        }
    }

    private void loadManager(Library library) throws SQLException {
        String sql = "SELECT * FROM manager";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                Manager manager = new Manager(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        Education.valueOf(rs.getString("education")),
                        rs.getInt("id")
                );
                library.setManager(manager);
            }
        }
    }

    private void loadRequests(Library library) throws SQLException {
        String sql = "SELECT * FROM requests";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int stdNumber = rs.getInt("studentStdNumber");
                int libId = rs.getInt("librarianId");
                int bookCode = rs.getInt("bookCode");
                RequestType type = RequestType.valueOf(rs.getString("requestType"));

                Book book = library.searchBook(bookCode);
                Student student = library.searchStudent(stdNumber);
                Librarian librarian = findLibrarianById(library, libId);

                if (book != null && student != null && librarian != null) {
                    library.addRequest(new Request(book, student, librarian, type));
                }
            }
        }
    }

    private void loadBorrows(Library library, String tableName) throws SQLException {
        String sql = "SELECT * FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student student = library.searchStudent(rs.getInt("studentStdNumber"));
                Book book = library.searchBook(rs.getInt("bookCode"));
                LocalDate borrowDate = LocalDate.parse(rs.getString("borrowDate"));
                LocalDate returnDate = rs.getString("returnDate") == null ?
                        null : LocalDate.parse(rs.getString("returnDate"));

                int borrowerId = rs.getInt("borrowerId");
                int returnerId = rs.getObject("returnerId") != null ?
                        rs.getInt("returnerId") : -1;

                Librarian borrower = findLibrarianById(library, borrowerId);
                Librarian returner = returnerId != -1 ?
                        findLibrarianById(library, returnerId) : null;

                if (student != null && book != null && borrower != null) {
                    Borrow borrow = new Borrow(book, student, borrowDate, borrower);
                    if (returnDate != null) {
                        borrow.setReturnDate(returnDate);
                    }
                    if (returner != null) {
                        borrow.setReturner(returner);
                    }

                    if (tableName.equals("borrows")) {
                        library.addBorrow(borrow);
                        if (returnDate == null) {
                            student.addBorrowedBook(book);
                        }
                    } else if (tableName.equals("borrowed_records")) {
                        library.addBorrowRecord(borrow);
                    } else if (tableName.equals("delayed_returns")) {
                        library.addDelayedReturn(borrow);
                    }
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