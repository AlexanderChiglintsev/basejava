/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = size() - 1; i >= 0; i--) {
            if (storage[i] != null) {
                storage[i] = null;
            } else break;
        }
    }

    void save(Resume r) {
        if (size() < 10000) {
            storage[size()] = r;
        } else {
            System.out.println("Storage is full !!!");
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i <= size() - 1; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int numdel = 0;
        for (int i = 0; i <= size() - 1; i++) {
            numdel++;
            if (storage[i].uuid.equals(uuid)) {
                break;
            }
        }
        storage[numdel - 1] = storage[size() - 1];
        storage[size() - 1] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allres = new Resume[size()];
        for (int i = 0; i <= size() - 1; i++) {
            if (storage[i] != null) {
                allres[i] = storage[i];
            } else {
                break;
            }
        }
        return allres;
    }

    int size() {
        int i = 0;
        for (Resume res : storage) {
            if (res != null) {
                i++;
            }
        }
        return i;
    }
}
