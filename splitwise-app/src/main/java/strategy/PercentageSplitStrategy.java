package strategy;

import enums.SplitType;
import exception.CannotCreateSplit;
import model.Expense;
import model.User;

import java.util.HashMap;
import java.util.List;

public class PercentageSplitStrategy implements  ISplitStrategy {
    public Expense createSplit(User paidBy, double amount, List<User> users, HashMap<User, Integer> userPercentageMap) {
        if (users.isEmpty() || userPercentageMap.size() != users.size()) {
            throw new CannotCreateSplit("Cannot create split as input data is corrupted");
        }
        if (!validatePercentageSum(userPercentageMap)) {
            throw new CannotCreateSplit("Cannot create split as input data is corrupted (% don't add upto 100)");
        }


        HashMap<User, Double> individualSplits = new HashMap<>();

        for (User user : users) {
            double individualAmount = amount * userPercentageMap.get(user) / 100;
            individualSplits.put(user, individualAmount);
        }

        return new Expense(paidBy, amount, users, SplitType.PERCENTAGE, individualSplits);
    }

    private boolean validatePercentageSum(HashMap<User, Integer> userPercentageMap) {
        int percentageSum = 0;
        for (User user : userPercentageMap.keySet()) percentageSum += userPercentageMap.get(user);

        return percentageSum == 100;
    }
}
