package service;

import exception.UserDoesNotBelongToGroup;
import model.PublicGroup;
import model.Expense;
import model.User;
import repository.GroupRepository;
import strategy.GreedyBalanceSheetSimplificationStrategy;
import strategy.IBalanceSheetSimplificationStrategy;

import java.util.HashMap;
import java.util.List;

public class GroupService {
    private final GroupRepository groupRepository;
    private IBalanceSheetSimplificationStrategy balanceSheetSimplificationStrategy;

    public GroupService() {
        this.groupRepository = new GroupRepository();
        this.balanceSheetSimplificationStrategy = new GreedyBalanceSheetSimplificationStrategy();
    }

    public void addGroup(PublicGroup group) {
        this.groupRepository.insert(group);
    }

    public void deleteGroup(String groupId) {
        this.groupRepository.delete(groupId);
    }

    public PublicGroup getGroup(String groupId) {
        return this.groupRepository.getById(groupId);
    }

    public List<PublicGroup> getAllGroups() {
        return this.groupRepository.getAll();
    }

    public void addExpense(String groupId, Expense expense) {
        PublicGroup group = this.groupRepository.getById(groupId);
        group.addExpense(expense);
    }

    public HashMap<User, Double> getGroupBalanceSheet(String groupId) {
        PublicGroup group = this.groupRepository.getById(groupId);
        return group.getGroupBalanceSheet();
    }

    public HashMap<User, Double> getBalanceSheetForUser(String groupId, User user) {
        PublicGroup group = this.groupRepository.getById(groupId);

        HashMap<User, HashMap<User, Double>> balanceSheet = group.getUserBalanceSheet();

        if (!balanceSheet.containsKey(user)) {
            throw new UserDoesNotBelongToGroup("User " + user + " does not belong to the group " + group);
        }

        return balanceSheet.get(user);
    }

    public void setBalanceSheetSimplificationStrategy(IBalanceSheetSimplificationStrategy balanceSheetSimplificationStrategy) {
        this.balanceSheetSimplificationStrategy = balanceSheetSimplificationStrategy;
    }

    public void simplify(String groupId) {
        PublicGroup group = this.groupRepository.getById(groupId);
        group.simplify(this.balanceSheetSimplificationStrategy);
    }
}
