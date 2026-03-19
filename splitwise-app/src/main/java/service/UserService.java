package service;

import model.PersonalGroup;
import model.Expense;
import model.User;
import repository.UserRepository;

import java.util.HashMap;
import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    private final HashMap<String, PersonalGroup> userPersonalGroups;

    public UserService() {
        this.userRepository = new UserRepository();
        this.userPersonalGroups = new HashMap<>();
    }

    public void addUser(User user) {
        this.userRepository.insert(user);
        userPersonalGroups.put(user.getId(), new PersonalGroup(user.getId() + "-group", user));
    }

    public void deleteUser(String userId) {
        this.userRepository.delete(userId);
        userPersonalGroups.remove(userId);
    }

    public List<User> getAllUsers() {
        return this.userRepository.getAll();
    }

    public User getUser(String userId) {
        return this.userRepository.getById(userId);
    }

    public void addExpense(String userId, Expense expense) {
        User user = this.userRepository.getById(userId);
        userPersonalGroups.get(user.getId()).addExpense(expense);
    }

    public HashMap<User, Double> getUserBalanceSheet(String userId) {
        User user = this.userRepository.getById(userId);
        return userPersonalGroups.get(user.getId()).getUserBalanceSheet();
    }
}
