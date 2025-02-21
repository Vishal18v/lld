package splitwise.Split;

import splitwise.User;

import java.util.List;

public class PercentageSplit extends Split{
    double percentage;
    public PercentageSplit(User user, double percentage) {
        super(user);
        this.percentage = percentage;
    }

    @Override
    public void calculateAmount(double totalAmount, int splitSize) {
        this.amount = (percentage / 100) * totalAmount;
    }
}
