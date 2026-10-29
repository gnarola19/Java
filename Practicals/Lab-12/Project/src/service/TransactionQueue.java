package service;

import java.util.LinkedList;
import java.util.Queue;

public class TransactionQueue {
    private final Queue<Runnable> buffer = new LinkedList<>();
    private final int capacity;
    private boolean finished = false;

    public TransactionQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void addTransaction(Runnable transaction)
            throws InterruptedException {
        while (buffer.size() >= capacity && !finished) {
            wait();
        }

        if (finished) {
            throw new IllegalStateException("Queue is closed.");
        }

        buffer.add(transaction);
        System.out.println("Transaction added to queue.");
        notifyAll();
    }

    public synchronized Runnable getTransaction() throws InterruptedException {
        while (buffer.isEmpty() && !finished) {
            wait();
        }

        if (buffer.isEmpty() && finished) {
            return null;
        }

        Runnable transaction = buffer.remove();
        notifyAll();
        return transaction;
    }

    public synchronized void finishAdding() {
        finished = true;
        notifyAll();
    }

    public void processTransactions() {
        try {
            Runnable transaction;

            while ((transaction = getTransaction()) != null) {
                transaction.run();
                System.out.println("Transaction processed.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Transaction processing interrupted.");
        }
    }
}