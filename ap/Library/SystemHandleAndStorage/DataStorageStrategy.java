package ap.Library.SystemHandleAndStorage;

import ap.Library.Library;

public interface DataStorageStrategy {
    void save(Library library);
    void load(Library library);
}
