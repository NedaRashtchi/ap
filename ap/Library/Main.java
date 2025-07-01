package ap.Library;

import ap.Library.Handler.LibrarySystem;

public class Main {
    public static void main(String[] args) {
        try {
            Manager manager = new Manager("Admin", "number1", Education.BACHELOR, 12345);
            Library library = new Library("University Library", manager);

            LibrarySystem system = new LibrarySystem(library);
            system.run();

        } catch (Exception e) {
            System.err.println("Error initializing system: " + e.getMessage());
        }
    }
}
