package ap.Library.SystemHandleAndStorage;

import ap.Library.Library;
import ap.Project.store.LocalDateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class JsonStorage implements DataStorageStrategy {
    @Override
    public void save(Library library) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter("libraryjson.json")) {
            gson.toJson(library, writer);
        }catch (IOException e){
            System.out.println("Error writing library test.json");
            e.printStackTrace();
        }
    }

    @Override
    public void load(Library library) {
        try (FileReader reader = new FileReader("libraryjson.json")) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                    .create();

            Library temp = gson.fromJson(reader, Library.class);

            if (temp != null) {
                library.setName(temp.getName());
                library.setManager(temp.getManager());
                library.setBooks(temp.getBooks());
                library.setStudents(temp.getStudents());
                library.setLibrarians(temp.getLibrarians());
                library.setLoans(temp.getLoans());
            }
        } catch (IOException e) {
            System.out.println("Error reading libraryjson.json: " + e.getMessage());

            library.setBooks(new HashMap<>());
            library.setStudents(new HashMap<>());
            library.setLibrarians(new HashMap<>());
            library.setLoans(new ArrayList<>());
        }
    }

}