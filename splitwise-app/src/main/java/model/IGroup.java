package model;

import enums.GroupType;

import java.util.List;

public interface IGroup {
    public String getId();
    public String getName();
    public GroupType getGroupType();
    public List<Expense> getExpenses();
    public void addExpense(Expense expense);
}
