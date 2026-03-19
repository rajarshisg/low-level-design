package strategy;

import model.User;

import java.util.HashMap;

public interface IBalanceSheetSimplificationStrategy {
    public HashMap<User, HashMap<User, Double>> simplify(HashMap<User, Double> groupBalanceSheet, HashMap<User, HashMap<User, Double>> userBalanceSheet);
}
