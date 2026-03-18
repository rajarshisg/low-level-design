package services;

import models.*;
import repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository repository;

    public UserService() {
        this.repository = new UserRepository();
    }

    public void create(String id, String name) {
        User user = (new User.UserBuilder())
                .id(id)
                .name(name)
                .build();

        this.repository.insert(user);
    }

    public void delete(String id) {
        this.repository.delete(id);
    }

    public User getUser(String id) {
        return this.repository.getById(id);
    }

    public List<User> getAllRiders() {
        return this.repository.getAll();
    }
}
