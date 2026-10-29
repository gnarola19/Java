package service;

public class TransferDeadlockDemo {

    static class BankAccount {
        private final String accountNumber;
        private long balance;

        BankAccount(String accountNumber, long balance) {
            this.accountNumber = accountNumber;
            this.balance = balance;
        }

        String getAccountNumber() {
            return accountNumber;
        }

        long getBalance() {
            return balance;
        }

        void transferTo(BankAccount target, long amount, boolean safe) {
            if (safe) {
                BankAccount first = accountNumber.compareTo(target.accountNumber) < 0
                        ? this : target;
                BankAccount second = first == this ? target : this;

                synchronized (first) {
                    synchronized (second) {
                        if (balance >= amount) {
                            balance -= amount;
                            target.balance += amount;
                        }
                    }
                }
            } else {
                synchronized (this) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }

                    synchronized (target) {
                        if (balance >= amount) {
                            balance -= amount;
                            target.balance += amount;
                        }
                    }
                }
            }
        }
    }

    static void demonstrateDeadlock() throws InterruptedException {
        BankAccount a = new BankAccount("A100", 1000);
        BankAccount b = new BankAccount("B200", 1000);

        Thread t1 = new Thread(() -> a.transferTo(b, 100, false), "A-to-B");
        Thread t2 = new Thread(() -> b.transferTo(a, 100, false), "B-to-A");

        t1.setDaemon(true);
        t2.setDaemon(true);

        t1.start();
        t2.start();

        Thread.sleep(500);

        System.out.println("Opposite lock ordering demonstration:");
        System.out.println("Thread A-to-B state: " + t1.getState());
        System.out.println("Thread B-to-A state: " + t2.getState());
        System.out.println("Opposite lock ordering can cause deadlock.");
    }

    static void demonstrateFix() throws InterruptedException {
        BankAccount a = new BankAccount("A100", 1000);
        BankAccount b = new BankAccount("B200", 1000);

        Thread t1 = new Thread(() -> a.transferTo(b, 100, true), "A-to-B");
        Thread t2 = new Thread(() -> b.transferTo(a, 100, true), "B-to-A");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("\nFixed version:");
        System.out.println("Account A balance: Rs. " + a.getBalance());
        System.out.println("Account B balance: Rs. " + b.getBalance());
        System.out.println("Both transfers completed without deadlock.");
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length > 0 && args[0].equals("deadlock")) {
            demonstrateDeadlock();
        } else {
            demonstrateFix();
        }
    }
}