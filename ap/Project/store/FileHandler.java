package ap.Project.store;

import ap.Project.Library;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileHandler {
    private DataStorageStrategy storageStrategy;

    public FileHandler() throws IOException {
        String storageType = readConfig();
        switch (storageType) {
            case "tabsplit":
                storageStrategy = new TabSplitStorage();
                break;
            case "json":
                storageStrategy = new JsonStorage();
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
    }
}