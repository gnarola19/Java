import model.Account;
import model.SavingsAccount;
import service.TransactionProcessor;

public class BatchTransactionDemo {

    public static void main(String[] args) throws InterruptedException {

        Account account = new SavingsAccount("Test Customer", 1000, 0);

        TransactionProcessor processor = new TransactionProcessor();
        processor.registerAccount(account);

        int numberOfDeposits = 100;
        long depositAmount = 10;

        System.out.println("===== CONCURRENT DEPOSIT TEST =====");
        System.out.println("Initial balance: Rs. " + account.getBalance());
        System.out.println("Number of deposits: " + numberOfDeposits);
        System.out.println("Each deposit: Rs. " + depositAmount);

        for (int i = 0; i < numberOfDeposits; i++) {
            processor.submitDeposit(
                    account.getAccountNumber(), depositAmount);
        }

        processor.stop();

        long expectedBalance =
                1000 + numberOfDeposits * depositAmount;

        System.out.println("Expected balance: Rs. " + expectedBalance);
        System.out.println("Actual balance: Rs. " + account.getBalance());

        if (account.getBalance() == expectedBalance) {
            System.out.println("PASS: All deposits processed correctly.");
        } else {
            System.out.println("FAIL: Balance mismatch.");
        }
    }
}