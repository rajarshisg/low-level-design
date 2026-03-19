package model;

import enums.SplitType;

import java.util.HashMap;
import java.util.List;

public class Expense {
    private final User paidBy;
    private final double amount;
    private final List<User> usersInSplit;
    private final HashMap<User, Double> userShares;
    private final SplitType splitType;

    public Expense(User paidBy, double amount, List<User> usersInSplit, SplitType splitType, HashMap<User, Double> userShares) {
        this.paidBy = paidBy;
        this.amount = amount;
        this.usersInSplit = usersInSplit;
        this.userShares = userShares;
        this.splitType = splitType;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public List<User> getUsersInSplit() {
        return usersInSplit;
    }

    public SplitType getSplitType() {
        return splitType;
    }

    public double getAmount() {
        return amount;
    }

    public HashMap<User, Double> getUserShares() {
        return userShares;
    }
}
