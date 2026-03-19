package model;

import enums.GroupType;
import exception.CannotRemoveUserFromGroup;
import exception.UserDoesNotBelongToGroup;
import strategy.IBalanceSheetSimplificationStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PublicGroup implements IGroup {
    private  final String id;
    private final String name;
    private final List<User> participants;
    private final List<Expense> expenses;
    private HashMap<User, Double> groupBalanceSheet;
    private HashMap<User, HashMap<User, Double>> userBalanceSheet;
    private final GroupType groupType;

    public PublicGroup(String id, String name) {
        this.id = id;
        this.name = name;
        this.participants = new ArrayList<>();
        this.expenses = new ArrayList<>();
        this.groupBalanceSheet = new HashMap<>();
        this.userBalanceSheet = new HashMap<>();
        this.groupType = GroupType.PUBLIC;
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

    public List<User> getParticipants() {
        return participants;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public HashMap<User, Double> getGroupBalanceSheet() {
        return groupBalanceSheet;
    }

    public HashMap<User, HashMap<User, Double>> getUserBalanceSheet() {
        return userBalanceSheet;
    }

    public void addParticipant(User user) {
        if (participants.contains(user)) return;

        participants.add(user);
        groupBalanceSheet.put(user, 0.0d);
        userBalanceSheet.put(user, new HashMap<User, Double>());
    }

    public void removeParticipant(User user) {
        if (!participants.contains(user)) {
            throw new UserDoesNotBelongToGroup("User " + user + " cannot be removed as they do not belong to the group " + this);
        }
        if (!isParticipantSettled(user)) {
            throw new CannotRemoveUserFromGroup("User " + user + " cannot be removed as they are not fully settled in the group " + this);
        }

        participants.remove(user);
        groupBalanceSheet.remove(user);
        userBalanceSheet.remove(user);
    }

    public boolean isParticipantSettled(User user) {
        if (groupBalanceSheet.get(user) > 1e-9) return false;

        // If someone owes to the user or the user is owing to someone they cannot be removed
        for (User currentUser : userBalanceSheet.keySet()) {
            if (currentUser.getId().equals(user.getId()) && !userBalanceSheet.get(user).isEmpty()) return false;
            if(userBalanceSheet.get(currentUser).containsKey(user)) return false;
        }

        return true;
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);

        User paidBy = expense.getPaidBy();
        HashMap<User, Double> individualSplits = expense.getUserShares();

        // PaidBy user must get back the total amount paid
        groupBalanceSheet.put(paidBy, groupBalanceSheet.get(paidBy) + expense.getAmount());

        // If PaidBy user is also part of the split, they must not get back the  amount they owe
        if (individualSplits.containsKey(paidBy)) groupBalanceSheet.put(paidBy, groupBalanceSheet.get(paidBy) - individualSplits.get(paidBy));

        // All users give back their splits to PaidBy user hence update the PaidBy user's balance sheet
        HashMap<User, Double> paidByUserBalanceSheet = userBalanceSheet.get(paidBy);
        for (User user : individualSplits.keySet()) {
            if (user.getId().equals(paidBy.getId())) continue;
            paidByUserBalanceSheet.put(user, paidByUserBalanceSheet.getOrDefault(user, 0.0d) + individualSplits.get(user));
        }

        // Add the individual splits expect for PaidBy user to be updated in group total balances
        for (User user : individualSplits.keySet()) {
            if (user.getId().equals(paidBy.getId())) continue;

            // user has to pay hence their net balance reduces
            groupBalanceSheet.put(user, groupBalanceSheet.get(user) - individualSplits.get(user));
        }

        // Add the individual splits expect for PaidBy user to individual balance sheets
        for (User user : individualSplits.keySet()) {
            if (user.getId().equals(paidBy.getId())) continue;;

            HashMap<User, Double> currUserBalanceSheet = userBalanceSheet.get(user);
            currUserBalanceSheet.put(paidBy, currUserBalanceSheet.getOrDefault(paidBy, 0.0d) - individualSplits.get(user));
        }
    }

    public void simplify(IBalanceSheetSimplificationStrategy balanceSheetSimplificationStrategy) {
        this.userBalanceSheet = balanceSheetSimplificationStrategy.simplify(groupBalanceSheet, userBalanceSheet);
        calculateGroupBalanceSheet();
    }

    private void calculateGroupBalanceSheet() {
        HashMap<User, Double> newGroupBalanceSheet = new HashMap<>();

        for (User user : this.userBalanceSheet.keySet()) {
            HashMap<User, Double> currUserBalanceSheet = this.userBalanceSheet.get(user);
            for (User toUser : currUserBalanceSheet.keySet()) {
                newGroupBalanceSheet.put(user, newGroupBalanceSheet.getOrDefault(user, 0.0d) + currUserBalanceSheet.get(toUser));
            }
        }

        this.groupBalanceSheet = newGroupBalanceSheet;
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
