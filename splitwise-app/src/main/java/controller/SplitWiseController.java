package controller;

import model.PublicGroup;
import model.Expense;
import model.User;
import service.GroupService;
import service.UserService;

import java.util.HashMap;
import java.util.List;

public class SplitWiseController {
    private static volatile SplitWiseController instance;
    private final UserService userService;
    private final GroupService groupService;

    private SplitWiseController() {
        this.userService = new UserService();
        this.groupService = new GroupService();
    }

    public static SplitWiseController getInstance() {
        if (instance != null) return instance;

        synchronized (SplitWiseController.class) {
            if (instance == null) instance = new SplitWiseController();
        }
        return instance;
    }

    // ---------------- USER ----------------

    public void addUser(String id, String name) {
        userService.addUser(new User(id, name));
    }

    public void deleteUser(String userId) {
        userService.deleteUser(userId);
    }

    public void getAllUsers() {
        List<User> users = userService.getAllUsers();
        System.out.println("All Users:");
        users.forEach(System.out::println);
    }

    public User getUser(String userId) {
        return userService.getUser(userId);
    }

    // ---------------- PERSONAL ----------------

    public void addExpenseInPersonalAccount(String userId, Expense expense) {
        userService.addExpense(userId, expense);
    }

    public HashMap<User, Double> getUserBalanceSheet(String userId) {
        return userService.getUserBalanceSheet(userId);
    }

    // ---------------- GROUP ----------------

    public void createGroup(String groupId, String name) {
        groupService.addGroup(new PublicGroup(groupId, name));
    }

    public void addUserToGroup(String groupId, String userId) {
        PublicGroup group = groupService.getGroup(groupId);
        User user = userService.getUser(userId);
        group.addParticipant(user);
    }

    public void addExpenseInGroup(String groupId, Expense expense) {
        groupService.addExpense(groupId, expense);
    }

    public void simplifyGroup(String groupId) {
        groupService.simplify(groupId);
    }

    public HashMap<User, Double> getGroupBalanceSheet(String groupId) {
        return groupService.getGroupBalanceSheet(groupId);
    }

    public HashMap<User, Double> getBalanceSheetForUserInGroup(String groupId, String userId) {
        User user = userService.getUser(userId);
        return groupService.getBalanceSheetForUser(groupId, user);
    }
}