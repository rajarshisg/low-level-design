package model;

import enums.GroupType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersonalGroup implements IGroup {
    private  final String id;
    private final String name;
    private final User user;
    private final List<Expense> expenses;
    private final HashMap<User, Double> userBalanceSheet;
    private final GroupType groupType;

    public PersonalGroup(String id, User user) {
        this.id = id;
        this.name = user.getName() + "'s non-group balance sheet";
        this.user = user;
        this.expenses = new ArrayList<>();
        this.userBalanceSheet = new HashMap<>();
        this.groupType = GroupType.PERSONAL;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }


    public HashMap<User, Double> getUserBalanceSheet() {
        return userBalanceSheet;
    }


    public void addExpense(Expense expense) {
        this.expenses.add(expense);

        User paidBy = expense.getPaidBy();
        HashMap<User, Double> individualSplits = expense.getUserShares();

        // All users give back their splits to PaidBy user hence update the PaidBy user's balance sheet
        for (User currUser : individualSplits.keySet()) {
            if (currUser.getId().equals(user.getId())) continue;

            double share = individualSplits.get(currUser);

            // If Group Owner is also PaidBy they receive else they owe
            if (paidBy.getId().equals(user.getId())) {
                userBalanceSheet.put(currUser, userBalanceSheet.getOrDefault(currUser, 0.0d) + share);
            } else {
                userBalanceSheet.put(currUser, userBalanceSheet.getOrDefault(currUser, 0.0d) - share);
            }

        }
    }

    @Override
    public String toString() {
        return "{id: " +
                id +
                ", name: " +
                name +
                "}";

    }
}
