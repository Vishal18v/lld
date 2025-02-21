package splitwise.Split;

import splitwise.User;

import java.util.List;

public class EqualSplit extends Split{
    public EqualSplit(User user) {
        super(user);
    }

    @Override
    public void calculateAmount(double totalAmount, int splitSize) {
        this.amount = totalAmount / splitSize;
    }
}
