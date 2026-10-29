package model;

import exception.*;
import model.annotation.Id;
import model.annotation.Positive;

public abstract class Account implements Transactable, InterestBearing {
    @Id
    protected String accountNumber;

    protected String holderName;

    @Positive
    protected long balance;

    protected boolean active = true;

    private static long nextAccountNumber = 1001;

    public Account(String holderName, long openingBalance) {
        this("ACC" + nextAccountNumber++, holderName, openingBalance);
    }
    public Account(String accountNumber, String holderName, long balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public long getBalance() { return balance; }
    public boolean isActive() { return active; }

    public void deactivate() { active = false; }

    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException(amount);
        if (active) balance += amount;
    }

    public synchronized boolean withdraw(long amount)
            throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException(amount);
        if (!active) return false;

        if (!canWithdraw(amount)) {
            long shortfall = Math.max(0, amount - balance);
            throw new InsufficientFundsException(shortfall);
        }

        balance -= amount;
        return true;
    }

    public void transfer(Account to, long amount) throws BankException {
        try {
            if (to == null) throw new AccountNotFoundException("null");
            if (amount <= 0) throw new InvalidAmountException(amount);

            if (withdraw(amount)) {
                to.deposit(amount);
                System.out.println("Transfer successful: Rs. " + amount);
            } else {
                throw new BankException("Source account is inactive.");
            }
        } catch (BankException e) {
            throw e;
        } finally {
            System.out.println("Transfer attempt completed.");
        }
    }

    public abstract boolean canWithdraw(long amount);

    @Override
    public String toString() {
        return "Account{number='" + accountNumber + "', holder='" +
                holderName + "', balance=Rs. " + balance + "}";
    }
}