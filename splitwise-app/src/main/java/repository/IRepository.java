package repository;

import java.util.List;

public interface IRepository<T> {
    public void insert(T item);
    public void delete(String id);
    public List<T> getAll();
    public T getById(String id);
}