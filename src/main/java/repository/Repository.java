package repository;

import java.util.List;
import java.util.UUID;

public interface Repository<T> {
    void add(T item);
    void update(T item);
    void remove(T item);
    T findById(UUID id);
    List<T> findAll();
}
