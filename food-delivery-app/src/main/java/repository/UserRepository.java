package repository;

import model.User;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository implements Repository<User> {
    ConcurrentHashMap<String, User> users;

    public UserRepository() {
        this.users = new ConcurrentHashMap<>();
    }

    public void insert(User user) {
        if (users.containsKey(user.getId())) {
            System.out.println("[ERROR] Cannot insert: User with ID " + user.getId() + " already exists!");
            return;
        }
        users.put(user.getId(), user);
    }

    public void delete(String id) {
        if (!users.containsKey(id)) {
            System.out.println("[ERROR] Cannot delete: User with ID " + id + " does not exist!");
            return;
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
            System.out.println("[ERROR] Cannot find: User with ID " + id + " does not exist!");
            return null;
        }

        return users.get(id);
    }
}