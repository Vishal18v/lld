package splitwise;

import java.util.HashMap;
import java.util.Map;

public class SplitwiseService {
    static SplitwiseService instance;
    HashMap<Integer, User> userList;
    HashMap<Integer, Group> groupList;

    static SplitwiseService getInstance() {
        if(instance==null) {
            return instance = new SplitwiseService();
        }
        return instance;
    }

    private SplitwiseService() {
        this.userList = new HashMap<>();
        this.groupList = new HashMap<>();
    }

    public HashMap<Integer, User> getUserList() {
        return userList;
    }

    public HashMap<Integer, Group> getGroupList() {
        return groupList;
    }

    public void addGroup(Integer groupId, Group group) {
        this.groupList.put(groupId, group);
    }

    public void addUser(Integer userId, User user) {
        this.userList.put(userId, user);
    }

    void groupBalance(int groupId) {
        Group group = groupList.get(groupId);
        HashMap<User, HashMap<User,Double>> groupBalance =  group.groupBalance();

        System.out.println("Balances for groupId:" + groupId);
        for (Map.Entry<User, HashMap<User, Double>> outerEntry : groupBalance.entrySet()) {
            User paidBy = outerEntry.getKey();
            HashMap<User, Double> balances = outerEntry.getValue();


            for (Map.Entry<User, Double> innerEntry : balances.entrySet()) {
                User owedUser = innerEntry.getKey();
                Double amount = innerEntry.getValue();
                System.out.println(owedUser.getName() +" owes " + paidBy.getName() + ": ₹" + amount);
            }
        }


    }

}
