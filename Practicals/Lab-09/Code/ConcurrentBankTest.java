public class ConcurrentBankTest {

    static class BankAccount {
        private long balance;

        BankAccount(long initialBalance) {
            balance = initialBalance;
        }

        public synchronized void deposit(long amount) {
            balance += amount;
        }

        public synchronized boolean withdraw(long amount) {
            if (amount <= 0 || balance < amount) {
                return false;
            }

            balance -= amount;
            return true;
        }

        public synchronized long getBalance() {
            return balance;
        }
    }

    static class DepositWorker implements Runnable {
        private final BankAccount account;
        private final int times;
        private final long amount;

        DepositWorker(BankAccount account, int times, long amount) {
            this.account = account;
            this.times = times;
            this.amount = amount;
        }

        @Override
        public void run() {
            for (int i = 0; i < times; i++) {
                account.deposit(amount);
            }
            System.out.println(Thread.currentThread().getName() + " finished depositing.");
        }
    }

    static class WithdrawWorker implements Runnable {
        private final BankAccount account;
        private final int times;
        private final long amount;

        WithdrawWorker(BankAccount account, int times, long amount) {
            this.account = account;
            this.times = times;
            this.amount = amount;
        }

        @Override
        public void run() {
            int successful = 0;

            for (int i = 0; i < times; i++) {
                if (account.withdraw(amount)) {
                    successful++;
                }
            }

            System.out.println(Thread.currentThread().getName()
                    + " completed " + successful + " successful withdrawals.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount(10000);

        Thread[] threads = new Thread[10];

        System.out.println("Initial balance: Rs. " + account.getBalance());

        // Five threads deposit Rs. 1,000 each, 100 times.
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(
                    new DepositWorker(account, 100, 1000),
                    "Depositor-" + (i + 1)
            );
        }

        // Five threads attempt to withdraw Rs. 500, 100 times.
        for (int i = 5; i < 10; i++) {
            threads[i] = new Thread(
                    new WithdrawWorker(account, 100, 500),
                    "Withdrawer-" + (i - 4)
            );
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("\nFinal balance: Rs. " + account.getBalance());
        System.out.println("All threads completed.");
    }
}