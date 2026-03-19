import controller.SplitWiseController;
import model.User;
import strategy.EqualSplitStrategy;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        SplitWiseController controller = SplitWiseController.getInstance();
        EqualSplitStrategy equalSplit = new EqualSplitStrategy();

        // Users
        controller.addUser("U1", "A");
        controller.addUser("U2", "B");
        controller.addUser("U3", "C");

        User A = controller.getUser("U1");
        User B = controller.getUser("U2");
        User C = controller.getUser("U3");

        // Group
        controller.createGroup("G1", "TestGroup");
        controller.addUserToGroup("G1", "U1");
        controller.addUserToGroup("G1", "U2");
        controller.addUserToGroup("G1", "U3");

        // ---- SPLITS ----

        // 1. A pays 300 (A,B,C)
        controller.addExpenseInGroup("G1",
                equalSplit.createSplit(A, 300, Arrays.asList(A, B, C)));

        // 2. B pays 150 (A,B,C)
        controller.addExpenseInGroup("G1",
                equalSplit.createSplit(B, 150, Arrays.asList(A, B, C)));

        // 3. C pays 210 (A,C)
        controller.addExpenseInGroup("G1",
                equalSplit.createSplit(C, 210, Arrays.asList(A, C)));

        // 4. A pays 120 (B only)
        controller.addExpenseInGroup("G1",
                equalSplit.createSplit(A, 120, Arrays.asList(B)));

        // ---- BEFORE SIMPLIFY ----
        System.out.println("\n--- BEFORE SIMPLIFY ---");
        controller.getGroupBalanceSheet("G1")
                .forEach((user, amt) ->
                        System.out.println(user.getName() + " : " + amt));

        System.out.println("A's sheet:");
        controller.getBalanceSheetForUserInGroup("G1", "U1")
                .forEach((user, amt) ->
                        System.out.println("  " + user.getName() + " : " + amt));

        System.out.println("B's sheet:");
        controller.getBalanceSheetForUserInGroup("G1", "U2")
                .forEach((user, amt) ->
                        System.out.println("  " + user.getName() + " : " + amt));

        System.out.println("C's sheet:");
        controller.getBalanceSheetForUserInGroup("G1", "U3")
                .forEach((user, amt) ->
                        System.out.println("  " + user.getName() + " : " + amt));

        // ---- SIMPLIFY ----
        controller.simplifyGroup("G1");

        // ---- AFTER SIMPLIFY ----
        System.out.println("\n--- AFTER SIMPLIFY ---");

        controller.getGroupBalanceSheet("G1")
                .forEach((user, amt) ->
                        System.out.println(user.getName() + " : " + amt));

        System.out.println("A's sheet:");
        controller.getBalanceSheetForUserInGroup("G1", "U1")
                .forEach((user, amt) ->
                        System.out.println("  " + user.getName() + " : " + amt));

        System.out.println("B's sheet:");
        controller.getBalanceSheetForUserInGroup("G1", "U2")
                .forEach((user, amt) ->
                        System.out.println("  " + user.getName() + " : " + amt));

        System.out.println("C's sheet:");
        controller.getBalanceSheetForUserInGroup("G1", "U3")
                .forEach((user, amt) ->
                        System.out.println("  " + user.getName() + " : " + amt));
    }
}