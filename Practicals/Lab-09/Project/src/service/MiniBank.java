package service;

import model.*;
import exception.*;

class BankSession implements AutoCloseable {
    public BankSession() {
        System.out.println("Bank session opened.");
    }

    @Override
    public void close() {
        System.out.println("Bank session closed.");
    }
}

public class MiniBank {
    public static void main(String[] args) {
        SavingsAccount savings = new SavingsAccount("Riya", 1000, 200);
        CurrentAccount current = new CurrentAccount("Priya", 5000, 3000);

        System.out.println("=== MINIBANK EXCEPTION HANDLING ===");
        System.out.println("Initial savings balance: Rs. " + savings.getBalance());

        try {
            savings.withdraw(5000);
        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
            System.out.println("Shortfall: Rs. " + e.getShortfall());
        } catch (InvalidAmountException e) {
            System.out.println("Invalid withdrawal: " + e.getMessage());
        } finally {
            System.out.println("Withdrawal attempt completed.");
        }

        try {
            savings.withdraw(0);
        } catch (InvalidAmountException e) {
            System.out.println("Invalid amount handled: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("Insufficient funds: " + e.getMessage());
        }

        try {
            savings.deposit(500);
            System.out.println("Deposit successful. New balance: Rs. " + savings.getBalance());

            current.transfer(savings, 200);
            System.out.println("Savings balance after transfer: Rs. " + savings.getBalance());
        } catch (BankException e) {
            System.out.println("Banking operation failed: " + e.getMessage());
        } finally {
            System.out.println("Banking operation block finished.");
        }

        try (BankSession session = new BankSession()) {
            System.out.println("Processing banking session...");
        } catch (Exception e) {
            System.out.println("Session error: " + e.getMessage());
        }

        System.out.println("MiniBank demonstration completed.");
    }
}