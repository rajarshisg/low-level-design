package repository;

import exception.AlreadyExistsException;
import exception.CannotFindException;
import model.User;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository implements IRepository<User> {
    ConcurrentHashMap<String, User> users;

    public UserRepository() {
        this.users = new ConcurrentHashMap<>();
    }

    public void insert(User user) {
        if (users.containsKey(user.getId())) {
            throw new AlreadyExistsException("Cannot insert: User " + user + " as it already exist in repository!");
        }
        users.put(user.getId(), user);
    }

    public void delete(String id) {
        if (!users.containsKey(id)) {
            throw new CannotFindException("Cannot delete: User with ID " + id + " as it do not exist in repository!");
        }

        users.remove(id);
    }

    public List<User> getAll() {
        return users
                .keySet()
                .stream()
                .map(id -> users.get(id))
                .toList();
    }

    public User getById(String id) {
        if (!users.containsKey(id)) {
            throw new CannotFindException("Cannot delete: User with ID " + id + " as it do not exist in repository!");
        }

        return users.get(id);
    }
}