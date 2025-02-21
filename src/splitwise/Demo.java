package splitwise;

import splitwise.Split.EqualSplit;
import splitwise.Split.ExactSplit;
import splitwise.Split.Split;
import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args){
        User user1 = new User(1, "vishal");
        User user2 = new User(2, "rahul");
        User user3 = new User(3, "mohit");

        List<Split> splits1 = Arrays.asList(new EqualSplit(user1), new EqualSplit(user2), new EqualSplit(user3));

        Expense expense1 = new Expense(1,120, user1, splits1," demo expense");

        List<Split> splits2 = Arrays.asList(new ExactSplit(user1, 50), new ExactSplit(user2, 150), new ExactSplit(user3, 100));
        Expense expense2 = new Expense(2, 300, user1, splits2, "demo expense2");

        List<Split> splits3 = Arrays.asList(new ExactSplit(user1, 500), new ExactSplit(user2, 100), new ExactSplit(user3, 800));
        Expense expense3 = new Expense(2, 1400, user3, splits3, "demo expense3");

        Group group1 = new Group(1, Arrays.asList(user1, user2, user3), List.of(expense1, expense2, expense3));

        SplitwiseService splitwiseService = SplitwiseService.getInstance();

        splitwiseService.addGroup(group1.getGroupId(), group1);
        splitwiseService.groupBalance(1);

    }
}
