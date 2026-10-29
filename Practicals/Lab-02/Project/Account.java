public class Account {

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static int counter = 0;

    // Constructor with opening balance
    public Account(String ownerName, long openingBalance) {
        this.accountNumber = String.format("AC%04d", ++counter);
        this.ownerName = ownerName;
        this.balance = Math.max(0, openingBalance);
        this.active = true;
    }

    // Constructor without opening balance
    public Account(String ownerName) {
        this(ownerName, 0);
    }

    // Deposit money
    public void deposit(long amount) {
        if (amount > 0 && active) {
            balance += amount;
        }
    }

    // Withdraw money
    public boolean withdraw(long amount) {
        if (amount > 0 && active && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // Getter methods
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }
}