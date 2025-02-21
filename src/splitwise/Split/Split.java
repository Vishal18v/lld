package splitwise.Split;

import splitwise.User;

import java.util.List;

abstract public class Split {
    User user;
    double amount;

    public Split(User user) {
        this.user = user;
        this.amount = 0;
    }

    public User getUser() {
        return user;
    }

    public double getAmount() {
        return amount;
    }

    public abstract void calculateAmount(double totalAmount, int splitSize);
}
