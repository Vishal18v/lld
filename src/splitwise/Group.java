package splitwise;

import splitwise.Split.Split;

import java.util.HashMap;
import java.util.List;

public class Group {
    int groupId;
    List<User> users;
    List<Expense> expenses;

    public Group(int groupId, List<User> users, List<Expense> expenses) {
        this.groupId = groupId;
        this.users = users;
        this.expenses = expenses;
    }

    public int getGroupId() {
        return groupId;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public HashMap<User, HashMap<User,Double>> groupBalance(){
        HashMap<User, HashMap<User,Double>> groupBalance = new HashMap<>();
        for(Expense expense: this.expenses){

            for(Split split: expense.getSplits()){
                User paidBy = expense.getPaidBy();
                User owedUser = split.getUser();

                if(paidBy.equals(owedUser))
                    continue;

                // Get or create the inner map for paidBy
                groupBalance.putIfAbsent(paidBy, new HashMap<>());

                // Get the current amount or default to 0
                Double currentAmount = groupBalance.get(paidBy).getOrDefault(owedUser, 0.0);

                // Update the amount
                groupBalance.get(paidBy).put(owedUser, currentAmount + split.getAmount());

            }
        }
        return groupBalance;

    }
}
