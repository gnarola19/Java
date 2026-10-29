package service;

import java.util.ArrayList;

import model.Account;
import exception.InvalidAmountException;
import exception.InsufficientFundsException;

public class BankService {

    private final Repository<Account> repository;

    public BankService(Repository<Account> repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }

    public Account openAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }

        if (repository.findById(account.getAccountNumber()) != null) {
            throw new IllegalArgumentException("Account already exists");
        }

        return repository.save(account);
    }

    public void deposit(String accountNumber, long amount)
            throws InvalidAmountException {
        Account account = getRequiredAccount(accountNumber);

        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        account.deposit(amount);
    }

    public void withdraw(String accountNumber, long amount)
            throws InvalidAmountException, InsufficientFundsException {
        Account account = getRequiredAccount(accountNumber);

        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (!account.withdraw(amount)) {
            throw new IllegalArgumentException("Withdrawal unsuccessful");
        }
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, long amount)
            throws InvalidAmountException, InsufficientFundsException {

        if (fromAccountNumber == null || toAccountNumber == null
                || fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Invalid transfer accounts");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        Account source = getRequiredAccount(fromAccountNumber);
        Account destination = getRequiredAccount(toAccountNumber);

        if (!source.withdraw(amount)) {
            throw new IllegalArgumentException("Transfer withdrawal unsuccessful");
        }

        try {
            destination.deposit(amount);
        } catch (InvalidAmountException | RuntimeException e) {
            source.deposit(amount);
            throw e;
        }
    }

    public String statement(String accountNumber) {
        Account account = getRequiredAccount(accountNumber);

        return "===== ACCOUNT STATEMENT =====\n"
                + "Account Number: " + account.getAccountNumber()
                + "\nBalance: Rs. " + account.getBalance();
    }

    public ArrayList<Account> listAccounts() {
        return repository.findAll();
    }

    public int accountCount() {
        return repository.count();
    }

    private Account getRequiredAccount(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }

        Account account = repository.findById(accountNumber);

        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountNumber);
        }

        return account;
    }
}