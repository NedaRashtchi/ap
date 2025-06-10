package ap.Project.store;

import ap.Project.Library;

public interface DataStorageStrategy {
    void save(Library library);
    void load(Library library);
}