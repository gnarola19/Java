package service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import model.Account;

public class TransactionProcessor {
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private final Map<String, Integer> taskCounts = new ConcurrentHashMap<>();

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
                    System.out.println("WARNING: Long-running task on "
                            + threadName + " took " + elapsed + " ms.");
                }
            }
        });
    }

    public void submitDeposit(Account account, long amount) {
        submit(() -> {
            try {
                account.deposit(amount);
                System.out.println(Thread.currentThread().getName()
                        + " deposited Rs. " + amount);
            } catch (Exception e) {
                System.out.println("Deposit failed: " + e.getMessage());
            }
        });
    }

    public void submitWithdrawal(Account account, long amount) {
        submit(() -> {
            try {
                boolean success = account.withdraw(amount);
                if (success) {
                    System.out.println(Thread.currentThread().getName()
                            + " withdrew Rs. " + amount);
                } else {
                    System.out.println("Withdrawal failed: account inactive.");
                }
            } catch (Exception e) {
                System.out.println("Withdrawal failed: " + e.getMessage());
            }
        });
    }

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
                System.out.println(thread + ": " + count + " tasks"));
    }
}