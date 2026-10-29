import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;
import service.Repository;
import service.BankService;
import exception.InvalidAmountException;
import exception.InsufficientFundsException;

public class MiniBankMenu {

    private static final Path DATA_FILE =
            Paths.get("data", "accounts.dat");

    private static void loadAccounts(Repository<Account> repository) {
        if (!Files.exists(DATA_FILE)) {
            System.out.println("No previous saved accounts found.");
            return;
        }

        try (ObjectInputStream input = new ObjectInputStream(
                Files.newInputStream(DATA_FILE))) {

            @SuppressWarnings("unchecked")
            ArrayList<Account> accounts =
                    (ArrayList<Account>) input.readObject();

            repository.saveAll(accounts.toArray(new Account[0]));
            System.out.println("Loaded " + accounts.size()
                    + " saved account(s).");

        } catch (IOException | ClassNotFoundException
                 | ClassCastException | IllegalArgumentException e) {
            System.out.println("Could not load saved accounts: "
                    + e.getMessage());
        }
    }

    private static void saveAccounts(Repository<Account> repository) {
        try {
            Files.createDirectories(DATA_FILE.getParent());

            try (ObjectOutputStream output = new ObjectOutputStream(
                    Files.newOutputStream(DATA_FILE))) {

                output.writeObject(repository.findAll());
            }

            System.out.println("Accounts saved to: "
                    + DATA_FILE.toAbsolutePath());

        } catch (IOException e) {
            System.out.println("Could not save accounts: "
                    + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Repository<Account> repository = new Repository<>();
        loadAccounts(repository);

        BankService bank = new BankService(repository);
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== MINIBANK MENU =====");
            System.out.println("1. Open Savings Account");
            System.out.println("2. Open Current Account");
            System.out.println("3. Open Fixed Deposit Account");
            System.out.println("4. Deposit");
            System.out.println("5. Withdraw");
            System.out.println("6. Transfer");
            System.out.println("7. Statement");
            System.out.println("8. List Accounts");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine().trim());

                switch (choice) {
                    case 1: {
                        System.out.print("Owner name: ");
                        String name = sc.nextLine();
                        System.out.print("Opening balance: ");
                        long balance = Long.parseLong(sc.nextLine());
                        System.out.print("Minimum balance: ");
                        long minimum = Long.parseLong(sc.nextLine());

                        Account account =
                                new SavingsAccount(name, balance, minimum);
                        bank.openAccount(account);
                        System.out.println("Account opened: "
                                + account.getAccountNumber());
                        break;
                    }

                    case 2: {
                        System.out.print("Owner name: ");
                        String name = sc.nextLine();
                        System.out.print("Opening balance: ");
                        long balance = Long.parseLong(sc.nextLine());
                        System.out.print("Overdraft limit: ");
                        long limit = Long.parseLong(sc.nextLine());

                        Account account =
                                new CurrentAccount(name, balance, limit);
                        bank.openAccount(account);
                        System.out.println("Account opened: "
                                + account.getAccountNumber());
                        break;
                    }

                    case 3: {
                        System.out.print("Owner name: ");
                        String name = sc.nextLine();
                        System.out.print("Opening balance: ");
                        long balance = Long.parseLong(sc.nextLine());

                        Account account =
                                new FixedDepositAccount(name, balance);
                        bank.openAccount(account);
                        System.out.println("Account opened: "
                                + account.getAccountNumber());
                        break;
                    }

                    case 4:
                        System.out.print("Account number: ");
                        String depositId = sc.nextLine();
                        System.out.print("Amount: ");
                        long depositAmount =
                                Long.parseLong(sc.nextLine());
                        bank.deposit(depositId, depositAmount);
                        System.out.println("Deposit successful.");
                        break;

                    case 5:
                        System.out.print("Account number: ");
                        String withdrawalId = sc.nextLine();
                        System.out.print("Amount: ");
                        long withdrawalAmount =
                                Long.parseLong(sc.nextLine());
                        bank.withdraw(withdrawalId, withdrawalAmount);
                        System.out.println("Withdrawal successful.");
                        break;

                    case 6:
                        System.out.print("Source account: ");
                        String source = sc.nextLine();
                        System.out.print("Destination account: ");
                        String destination = sc.nextLine();
                        System.out.print("Amount: ");
                        long transferAmount =
                                Long.parseLong(sc.nextLine());
                        bank.transfer(source, destination, transferAmount);
                        System.out.println("Transfer successful.");
                        break;

                    case 7:
                        System.out.print("Account number: ");
                        System.out.println(bank.statement(sc.nextLine()));
                        break;

                    case 8:
                        System.out.println("\n===== ALL ACCOUNTS =====");
                        for (Account account : bank.listAccounts()) {
                            System.out.println(account);
                        }
                        System.out.println("Total accounts: "
                                + bank.accountCount());
                        break;

                    case 9:
                        saveAccounts(repository);
                        running = false;
                        System.out.println("Exiting MiniBank.");
                        break;

                    default:
                        System.out.println("Invalid menu choice.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            } catch (InvalidAmountException
                     | InsufficientFundsException e) {
                System.out.println("Transaction failed: "
                        + e.getMessage());
            } catch (Exception e) {
                System.out.println("Operation failed: "
                        + e.getMessage());
            }
        }

        sc.close();
    }
}