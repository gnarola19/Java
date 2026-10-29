import java.util.ArrayList;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;
import service.Repository;

public class RepositoryDemo {

    public static void main(String[] args) {

        Repository<Account> repository = new Repository<>();

        Account a1 = new SavingsAccount("Aarav", 5000, 1000);
        Account a2 = new CurrentAccount("Diya", 9000, 2000);
        Account a3 = new FixedDepositAccount("Krish", 2000);

        repository.saveAll(a1, a2, a3);

        System.out.println("===== GENERIC REPOSITORY =====");

        Account found = repository.findById(a2.getAccountNumber());

        System.out.println("\nAccount found:");
        System.out.println(found);

        System.out.println("\nAll accounts:");

        ArrayList<Account> accounts = repository.findAll();

        for (Account account : accounts) {
            System.out.println(account);
        }

        System.out.println("\nTotal accounts: " + repository.count());

        Integer[] numbers = {10, 20, 30};
        System.out.println("Average: " + Repository.average(numbers));

        boolean deleted = repository.delete(a3.getAccountNumber());

        System.out.println("\nAccount deleted: " + deleted);
        System.out.println("Accounts remaining: " + repository.count());
    }
}