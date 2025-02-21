package splitwise;

import splitwise.Split.Split;

import java.util.List;

public class Expense {
    int expenseId;
    double amount;
    User paidBy;

    List<Split> splits;
    String description;

    public Expense(int expenseId, double amount, User paidBy, List<Split> splits, String description) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.paidBy = paidBy;
        this.splits = splits;
        this.description = description;
        calculateSplits();
    }

    // Compute how much each user should pay
    private void calculateSplits() {
        for (Split split : splits) {
            split.calculateAmount(amount, splits.size());
        }
    }

    public int getExpenseId() {
        return expenseId;
    }

    public double getAmount() {
        return amount;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public String getDescription() {
        return description;
    }

    public User getPaidBy() {
        return paidBy;
    }

}
