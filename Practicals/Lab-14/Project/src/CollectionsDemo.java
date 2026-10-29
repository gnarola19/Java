import java.util.ArrayList;
import java.util.TreeMap;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;
import service.AccountStore;
import service.TransactionProcessor;

public class CollectionsDemo {

    public static void main(String[] args) throws InterruptedException {

        AccountStore store = new AccountStore();

        Account a1 = new SavingsAccount("Aarav", 5000, 1000);
        Account a2 = new CurrentAccount("Diya", 2000, 5000);
        Account a3 = new FixedDepositAccount("Krish", 9000);

        store.add(a1);
        store.add(a2);
        store.add(a3);

        System.out.println("===== ACCOUNT STORE =====");
        System.out.println("Total accounts: " + store.size());

        System.out.println("\nAccount retrieved by number:");
        Account found = store.get(a2.getAccountNumber());
        System.out.println(found);

        System.out.println("\nAccounts sorted by balance (highest first):");
        ArrayList<Account> sorted = store.sortedByBalance();

        for (Account account : sorted) {
            System.out.println(
                account.getAccountNumber() + " | Balance: Rs. "
                + account.getBalance()
            );
        }

        System.out.println("\nAccounts with balance above Rs. 3000:");
        for (Account account : store.getAccountsAboveBalance(3000)) {
            System.out.println(account);
        }

        System.out.println("\n===== TREEMAP =====");
        TreeMap<String, Account> treeMap = new TreeMap<>();

        for (Account account : store.getAll()) {
            treeMap.put(account.getAccountNumber(), account);
        }

        treeMap.forEach((number, account) ->
            System.out.println(number + " -> " + account)
        );

        System.out.println("\n===== TRANSACTION PROCESSOR =====");
        TransactionProcessor processor = new TransactionProcessor();

        for (Account account : store.getAll()) {
            processor.registerAccount(account);
        }

        Account shared = processor.getAccount(a1.getAccountNumber());

        System.out.println("Retrieved from ConcurrentHashMap: " + shared);

        processor.submitDeposit(a1.getAccountNumber(), 500);
        processor.submitWithdrawal(a2.getAccountNumber(), 200);

        processor.stop();

        System.out.println("\n===== FINAL BALANCES =====");
        for (Account account : store.sortedByBalance()) {
            System.out.println(
                account.getAccountNumber() + " | Balance: Rs. "
                + account.getBalance()
            );
        }

        System.out.println("\nP12 Collections Demo completed.");
    }
}