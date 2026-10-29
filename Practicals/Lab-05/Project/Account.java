import java.util.Objects;

public abstract class Account {

    private final String accountNumber;
    private String ownerName;
    protected long balance;
    private boolean active;

    private static int counter = 0;

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = String.format("AC%04d", ++counter);
        this.ownerName = ownerName;
        this.balance = Math.max(0, openingBalance);
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    // Abstract methods
    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    // Deposit money
    public void deposit(long amount) {
        if (amount > 0 && active) {
            balance += amount;
        }
    }

    // Withdraw money
    public boolean withdraw(long amount) {
        if (amount > 0 && active && canWithdraw(amount)) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // Calculate monthly interest
    public double monthlyInterest() {
        return balance * interestRate() / 100.0 / 12.0;
    }

    // Getters
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

    // Display account details
    @Override
    public String toString() {
        return accountNumber + " | " + ownerName
                + " | Balance: " + balance
                + " | Active: " + active;
    }

    // Compare accounts by account number
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Account)) {
            return false;
        }

        Account other = (Account) obj;
        return accountNumber.equals(other.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}