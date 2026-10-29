package service;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import model.Account;

public class TransactionProcessor {

    private final ExecutorService executor =
            Executors.newFixedThreadPool(4);

    // Thread-safe shared account storage
    private final ConcurrentHashMap<String, Account> accounts =
            new ConcurrentHashMap<>();

    // Thread-safe task counter
    private final Map<String, Integer> taskCounts =
            new ConcurrentHashMap<>();

    // Register an account in shared storage
    public void registerAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    // Retrieve an account using its account number
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    // Retrieve all registered accounts
    public ArrayList<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    // Submit a general task to the thread pool
    public void submit(Runnable task) {
        executor.execute(() -> {
            String threadName = Thread.currentThread().getName();

            taskCounts.merge(threadName, 1, Integer::sum);

            long start = System.currentTimeMillis();

            try {
                task.run();
            } finally {
                long elapsed = System.currentTimeMillis() - start;

                if (elapsed > 1000) {
                    System.out.println(
                            "WARNING: Long-running task on "
                            + threadName + " took " + elapsed + " ms."
                    );
                }
            }
        });
    }

    // Deposit using an Account object
    public void submitDeposit(Account account, long amount) {
        registerAccount(account);
        submitDeposit(account.getAccountNumber(), amount);
    }

    // Deposit using an account number
    public void submitDeposit(String accountNumber, long amount) {
        submit(() -> {
            Account account = accounts.get(accountNumber);

            if (account == null) {
                System.out.println("Deposit failed: account not found.");
                return;
            }

            try {
                account.deposit(amount);

                System.out.println(
                        Thread.currentThread().getName()
                        + " deposited Rs. " + amount
                        + " into " + accountNumber
                );
            } catch (Exception e) {
                System.out.println("Deposit failed: " + e.getMessage());
            }
        });
    }

    // Withdrawal using an Account object
    public void submitWithdrawal(Account account, long amount) {
        registerAccount(account);
        submitWithdrawal(account.getAccountNumber(), amount);
    }

    // Withdrawal using an account number
    public void submitWithdrawal(String accountNumber, long amount) {
        submit(() -> {
            Account account = accounts.get(accountNumber);

            if (account == null) {
                System.out.println("Withdrawal failed: account not found.");
                return;
            }

            try {
                boolean success = account.withdraw(amount);

                if (success) {
                    System.out.println(
                            Thread.currentThread().getName()
                            + " withdrew Rs. " + amount
                            + " from " + accountNumber
                    );
                } else {
                    System.out.println(
                            "Withdrawal failed: account inactive."
                    );
                }
            } catch (Exception e) {
                System.out.println(
                        "Withdrawal failed: " + e.getMessage()
                );
            }
        });
    }

    // Stop the thread pool and display task counts
    public void stop() throws InterruptedException {
        executor.shutdown();

        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
            executor.shutdownNow();

            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Some tasks did not terminate.");
            }
        }

        System.out.println("\nTask counts by pool thread:");

        taskCounts.forEach((thread, count) ->
                System.out.println(thread + ": " + count + " tasks")
        );
    }
}