import java.util.Objects;

public class Account {

    private final String accountNumber;
    private String ownerName;
    private long balance;
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

    // Constructor for testing equals()
    Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = Math.max(0, balance);
        this.active = true;
    }

    public void deposit(long amount) {
        if (amount > 0 && active) {
            balance += amount;
        }
    }

    public boolean withdraw(long amount) {
        if (amount > 0 && active && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

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

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName
                + " | " + balance + " | Active: " + active;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Account))
            return false;

        Account other = (Account) obj;
        return accountNumber.equals(other.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}