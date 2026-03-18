package service;

import model.User;
import repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(String id, String name) {
        userRepository.insert(new User(id, name));
        return true;
    }

    public User getUser(String id) {
        return userRepository.getById(id);
    }
}