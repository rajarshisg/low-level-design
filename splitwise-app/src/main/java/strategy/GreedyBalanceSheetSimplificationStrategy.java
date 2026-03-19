package strategy;

import model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

public class GreedyBalanceSheetSimplificationStrategy implements IBalanceSheetSimplificationStrategy {
    private static class Pair implements  Comparable<Pair> {
        private final User user;
        private Double amount;

        public Pair(User user, Double amount) {
            this.user = user;
            this.amount = amount;
        }

        public User getUser() {
            return user;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public int compareTo(Pair next) {
            return Double.compare(this.getAmount(), next.getAmount());
        }
    }

    public HashMap<User, HashMap<User, Double>> simplify(HashMap<User, Double> groupBalanceSheet, HashMap<User, HashMap<User, Double>> userBalanceSheet) {
        HashMap<User, HashMap<User, Double>> optimizedBalanceSheet = new HashMap<>();
        for (User user : userBalanceSheet.keySet()) optimizedBalanceSheet.put(user, new HashMap<>());

        PriorityQueue<Pair> owePq = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Pair> receivePq = new PriorityQueue<>(Collections.reverseOrder());

        for (User user : groupBalanceSheet.keySet()) {
            double amount = groupBalanceSheet.get(user);
            if (amount < 0) owePq.add(new Pair(user, -1.0d * amount));
            else if (amount > 0) receivePq.add(new Pair(user, amount));
        }

        while (!owePq.isEmpty() && !receivePq.isEmpty()) {
            Pair currOwe = owePq.poll();
            Pair currReceive = receivePq.poll();

            assert currReceive != null;
            assert currOwe != null;
            User oweUser = currOwe.getUser();
            User receiveUser = currReceive.getUser();
            double oweAmount = currOwe.getAmount();
            double receiveAmount = currReceive.getAmount();


            HashMap<User, Double> owedUserBalanceSheet = optimizedBalanceSheet.get(oweUser);
            HashMap<User, Double> receiveUserBalanceSheet = optimizedBalanceSheet.get(receiveUser);

            owedUserBalanceSheet.put(receiveUser, owedUserBalanceSheet.getOrDefault(receiveUser, 0.0d) - Math.min(oweAmount, receiveAmount));
            receiveUserBalanceSheet.put(oweUser, receiveUserBalanceSheet.getOrDefault(oweUser, 0.0d) + Math.min(oweAmount, receiveAmount));

            if (oweAmount < receiveAmount) {
                receivePq.add(new Pair(receiveUser, receiveAmount - oweAmount));
            } else if (oweAmount > receiveAmount) {
                owePq.add(new Pair(oweUser, oweAmount - receiveAmount));
            }
        }

        return optimizedBalanceSheet;
    }
}
