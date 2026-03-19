package strategy;

import enums.SplitType;
import exception.CannotCreateSplit;
import model.Expense;
import model.User;

import java.util.HashMap;
import java.util.List;

public class EqualSplitStrategy implements ISplitStrategy {
    public Expense createSplit(User paidBy, double amount, List<User> users) {
        if (users.isEmpty()) {
            throw new CannotCreateSplit("Cannot create split as users size is 0");
        }

        HashMap<User, Double> individualSplits = new HashMap<>();

        int totalUsers = users.size();
        double individualAmount = amount / totalUsers;

        for (User user : users) {
            individualSplits.put(user, individualAmount);
        }

        return new Expense(paidBy, amount, users, SplitType.EQUAL, individualSplits);
    }
}
