package service;

import model.SavingsAccount;

public class MiniBank {
    public static void main(String[] args) throws InterruptedException {
        SavingsAccount account = new SavingsAccount("TestAccount", 10000, 0);

        System.out.println("=== MINI BANK TRANSACTION ENGINE ===");
        System.out.println("Initial balance: Rs. " + account.getBalance());

        TransactionProcessor processor = new TransactionProcessor();

        for (int i = 1; i <= 10; i++) {
            processor.submitDeposit(account, 100);
        }

        for (int i = 1; i <= 5; i++) {
            processor.submitWithdrawal(account, 50);
        }

        processor.stop();

        System.out.println("\nBalance after batch transactions: Rs. "
                + account.getBalance());

        System.out.println("\n=== PRODUCER-CONSUMER QUEUE ===");

        TransactionQueue queue = new TransactionQueue(3);

        Thread consumer = new Thread(queue::processTransactions, "TransactionConsumer");
        consumer.start();

        for (int i = 1; i <= 5; i++) {
            final int transactionId = i;

            queue.addTransaction(() ->
                    System.out.println("Processing transaction #" + transactionId));
        }

        queue.finishAdding();
        consumer.join();

        System.out.println("All queued transactions processed.");
    }
}