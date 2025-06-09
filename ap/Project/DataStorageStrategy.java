package ap.Project;

public interface DataStorageStrategy {
    void save(Library library);
    void load(Library library);
}