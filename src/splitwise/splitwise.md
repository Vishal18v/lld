Got it! Instead of a `validateExpense` method in `Expense`, I'll add a **`calculateSplits`** method to dynamically compute the split amounts based on the selected split strategy (**Equal, Exact, or Percentage**).  

Here's the **updated design** following SOLID principles:

---

### **🔹 Class 1: `User`**
```java
import java.util.*;

class User {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;

    public User(String userId, String name, String email, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
}
```

---

### **🔹 Class 2: `Split` (Abstract)**
```java
abstract class Split {
    protected User user;
    protected double amount;  // This will be calculated

    public Split(User user) {
        this.user = user;
        this.amount = 0; // Will be computed later
    }

    public User getUser() { return user; }
    public double getAmount() { return amount; }

    public abstract void calculateAmount(double totalAmount, List<Split> splits);
}
```

---

### **🔹 Class 3: `EqualSplit` (Each user pays equally)**
```java
class EqualSplit extends Split {
    public EqualSplit(User user) { super(user); }

    @Override
    public void calculateAmount(double totalAmount, List<Split> splits) {
        this.amount = totalAmount / splits.size();
    }
}
```

---

### **🔹 Class 4: `ExactSplit` (Each user pays a fixed amount)**
```java
class ExactSplit extends Split {
    public ExactSplit(User user, double amount) {
        super(user);
        this.amount = amount; // Already provided
    }

    @Override
    public void calculateAmount(double totalAmount, List<Split> splits) {
        // No need to calculate; already set
    }
}
```

---

### **🔹 Class 5: `PercentageSplit` (Each user pays based on percentage)**
```java
class PercentageSplit extends Split {
    private double percentage;

    public PercentageSplit(User user, double percentage) {
        super(user);
        this.percentage = percentage;
    }

    public double getPercentage() { return percentage; }

    @Override
    public void calculateAmount(double totalAmount, List<Split> splits) {
        this.amount = (percentage / 100) * totalAmount;
    }
}
```

---

### **🔹 Class 6: `Expense` (Handles amount distribution)**
```java
class Expense {
    private String expenseId;
    private User paidBy;
    private double amount;
    private Group group;
    private List<Split> splits;

    public Expense(String expenseId, User paidBy, double amount, Group group, List<Split> splits) {
        this.expenseId = expenseId;
        this.paidBy = paidBy;
        this.amount = amount;
        this.group = group;
        this.splits = splits;
        calculateSplits();
    }

    // Compute how much each user should pay
    private void calculateSplits() {
        for (Split split : splits) {
            split.calculateAmount(amount, splits);
        }
    }

    // Getters
    public String getExpenseId() { return expenseId; }
    public User getPaidBy() { return paidBy; }
    public double getAmount() { return amount; }
    public List<Split> getSplits() { return splits; }
}
```

---

### **🔹 Class 7: `Group`**
```java
class Group {
    private String groupId;
    private String name;
    private List<User> users;
    private List<Expense> expenses;

    public Group(String groupId, String name) {
        this.groupId = groupId;
        this.name = name;
        this.users = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    // Add or remove users
    public void addUser(User user) { users.add(user); }
    public void removeUser(User user) { users.remove(user); }

    // Add Expense
    public void addExpense(Expense expense) { expenses.add(expense); }

    // Getters
    public List<User> getUsers() { return users; }
    public List<Expense> getExpenses() { return expenses; }
}
```

---

### **🔹 Class 8: `SplitwiseService`**
```java
class SplitwiseService {
    private Map<User, Map<User, Double>> balanceSheet;

    public SplitwiseService() {
        this.balanceSheet = new HashMap<>();
    }

    public void addExpense(Expense expense) {
        User paidBy = expense.getPaidBy();
        double amount = expense.getAmount();
        List<Split> splits = expense.getSplits();

        for (Split split : splits) {
            User user = split.getUser();
            double splitAmount = split.getAmount();

            balanceSheet.putIfAbsent(user, new HashMap<>());
            balanceSheet.putIfAbsent(paidBy, new HashMap<>());

            balanceSheet.get(user).put(paidBy, balanceSheet.get(user).getOrDefault(paidBy, 0.0) + splitAmount);
            balanceSheet.get(paidBy).put(user, balanceSheet.get(paidBy).getOrDefault(user, 0.0) - splitAmount);
        }
    }

    public double getBalance(User user1, User user2) {
        return balanceSheet.getOrDefault(user1, new HashMap<>()).getOrDefault(user2, 0.0);
    }

    public void settleUp(User payer, User payee, double amount) {
        double balance = getBalance(payer, payee);
        if (balance < amount) {
            System.out.println("Invalid settlement amount");
            return;
        }

        System.out.println(payer.getName() + " paid " + amount + " to " + payee.getName());

        balanceSheet.get(payer).put(payee, balance - amount);
        balanceSheet.get(payee).put(payer, -balance + amount);
    }
}
```

---

### **🔹 Example Usage**
```java
public class Main {
    public static void main(String[] args) {
        User alice = new User("U1", "Alice", "alice@example.com", "1234567890");
        User bob = new User("U2", "Bob", "bob@example.com", "0987654321");
        User charlie = new User("U3", "Charlie", "charlie@example.com", "5678901234");

        SplitwiseService service = new SplitwiseService();

        // Create Equal Expense
        List<Split> equalSplits = Arrays.asList(new EqualSplit(alice), new EqualSplit(bob), new EqualSplit(charlie));
        Expense equalExpense = new Expense("E1", alice, 300, null, equalSplits);
        service.addExpense(equalExpense);

        // Create Exact Expense
        List<Split> exactSplits = Arrays.asList(new ExactSplit(alice, 50), new ExactSplit(bob, 150), new ExactSplit(charlie, 100));
        Expense exactExpense = new Expense("E2", bob, 300, null, exactSplits);
        service.addExpense(exactExpense);

        // Create Percentage Expense
        List<Split> percentageSplits = Arrays.asList(new PercentageSplit(alice, 50), new PercentageSplit(bob, 30), new PercentageSplit(charlie, 20));
        Expense percentageExpense = new Expense("E3", charlie, 200, null, percentageSplits);
        service.addExpense(percentageExpense);

        // Print balances
        System.out.println("Balance of Bob from Alice: " + service.getBalance(bob, alice));
        System.out.println("Balance of Charlie from Bob: " + service.getBalance(charlie, bob));

        // Settle up
        service.settleUp(bob, alice, 50);
    }
}
```

---

## **✅ Summary**
✔ Dynamically calculates **splits** based on Equal, Exact, or Percentage  
✔ **SOLID Principles** applied  
✔ **Scalable, maintainable, and testable**  

Would you like me to **add REST API endpoints** for this? 🚀