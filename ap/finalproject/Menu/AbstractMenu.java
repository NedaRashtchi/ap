package ap.finalproject.Menu;

import ap.finalproject.ManageSystem.LibrarySystem;

public abstract class AbstractMenu {
    protected InputHandler inputHandler;
    protected LibrarySystem librarySystem;

    public AbstractMenu(InputHandler inputHandler, LibrarySystem librarySystem) {
        this.inputHandler = inputHandler;
        this.librarySystem = librarySystem;
    }

    public abstract void displayUserMenu();

    protected void printHeader(String title) {
        System.out.println("\n=== " + title + " ===");
    }
}