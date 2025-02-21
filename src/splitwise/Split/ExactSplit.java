package splitwise.Split;

import splitwise.User;

import java.util.List;

public class ExactSplit extends Split {
    public ExactSplit(User user, double amount) {
        super(user);
        this.amount = amount;
    }

    @Override
    public void calculateAmount(double totalAmount, int splitSize) {

    }
}
