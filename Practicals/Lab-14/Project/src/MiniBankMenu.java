import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
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
    private static final Path REPORT_FILE =
            Paths.get("data", "end-of-day-report.txt");

    private static long totalDeposits = 0;
    private static long totalWithdrawals = 0;
    private static final ArrayList<String> transactions =
            new ArrayList<>();

    private static void loadAccounts(Repository<Account> repository) {
        if (!Files.exists(DATA_FILE)) {
            System.out.println("No previous saved accounts found.");
            return;
        }

        try (ObjectInputStream input = new ObjectInputStream(
                Files.newInputStream(DATA_FILE))) {

            Object object = input.readObject();

            if (!(object instanceof ArrayList<?>)) {
                System.out.println("Invalid account data file.");
                return;
            }

            ArrayList<?> loaded = (ArrayList<?>) object;
            int count = 0;

            for (Object item : loaded) {
                if (item instanceof Account) {
                    repository.save((Account) item);
                    count++;
                }
            }

            System.out.println("Loaded " + count + " account(s).");

        } catch (IOException | ClassNotFoundException
                 | IllegalArgumentException e) {
            System.out.println("Could not load accounts: "
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

            System.out.println("Accounts saved successfully.");

        } catch (IOException e) {
            System.out.println("Could not save accounts: "
                    + e.getMessage());
        }
    }

    private static void generateReport() {
        long netChange = totalDeposits - totalWithdrawals;

        StringBuilder report = new StringBuilder();
        report.append("===== MINIBANK END-OF-DAY REPORT =====\n");
        report.append("Generated: ").append(LocalDateTime.now()).append('\n');
        report.append("Total Deposits: Rs. ").append(totalDeposits).append('\n');
        report.append("Total Withdrawals: Rs. ").append(totalWithdrawals).append('\n');
        report.append("Net Change: Rs. ").append(netChange).append('\n');
        report.append("\nTransaction History:\n");

        if (transactions.isEmpty()) {
            report.append("No transactions in this session.\n");
        } else {
            for (String transaction : transactions) {
                report.append(transaction).append('\n');
            }
        }

        System.out.println("\n" + report);

        try {
            Files.createDirectories(REPORT_FILE.getParent());
            Files.writeString(REPORT_FILE, report.toString());
            System.out.println("Report saved to: "
                    + REPORT_FILE.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Could not save report: "
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
            System.out.println("9. Generate End-of-Day Report");
            System.out.println("10. Save and Exit");
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

                        Account account = new SavingsAccount(
                                name, balance, minimum);
                        bank.openAccount(account);
                        System.out.println("Opened: "
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

                        Account account = new CurrentAccount(
                                name, balance, limit);
                        bank.openAccount(account);
                        System.out.println("Opened: "
                                + account.getAccountNumber());
                        break;
                    }

                    case 3: {
                        System.out.print("Owner name: ");
                        String name = sc.nextLine();
                        System.out.print("Opening balance: ");
                        long balance = Long.parseLong(sc.nextLine());

                        Account account = new FixedDepositAccount(
                                name, balance);
                        bank.openAccount(account);
                        System.out.println("Opened: "
                                + account.getAccountNumber());
                        break;
                    }

                    case 4: {
                        System.out.print("Account number: ");
                        String id = sc.nextLine();
                        System.out.print("Amount: ");
                        long amount = Long.parseLong(sc.nextLine());

                        bank.deposit(id, amount);
                        totalDeposits = Math.addExact(totalDeposits, amount);
                        transactions.add("Deposit: " + id
                                + " | Rs. " + amount);
                        System.out.println("Deposit successful.");
                        break;
                    }

                    case 5: {
                        System.out.print("Account number: ");
                        String id = sc.nextLine();
                        System.out.print("Amount: ");
                        long amount = Long.parseLong(sc.nextLine());

                        bank.withdraw(id, amount);
                        totalWithdrawals =
                                Math.addExact(totalWithdrawals, amount);
                        transactions.add("Withdrawal: " + id
                                + " | Rs. " + amount);
                        System.out.println("Withdrawal successful.");
                        break;
                    }

                    case 6: {
                        System.out.print("Source account: ");
                        String from = sc.nextLine();
                        System.out.print("Destination account: ");
                        String to = sc.nextLine();
                        System.out.print("Amount: ");
                        long amount = Long.parseLong(sc.nextLine());

                        bank.transfer(from, to, amount);
                        transactions.add("Transfer: " + from + " -> "
                                + to + " | Rs. " + amount);
                        System.out.println("Transfer successful.");
                        break;
                    }

                    case 7:
                        System.out.print("Account number: ");
                        System.out.println(bank.statement(sc.nextLine()));
                        break;

                    case 8:
                        for (Account account : bank.listAccounts()) {
                            System.out.println(account);
                        }
                        System.out.println("Total accounts: "
                                + bank.accountCount());
                        break;

                    case 9:
                        generateReport();
                        break;

                    case 10:
                        saveAccounts(repository);
                        running = false;
                        System.out.println("Exiting MiniBank.");
                        break;

                    default:
                        System.out.println("Invalid menu choice.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (InvalidAmountException
                     | InsufficientFundsException e) {
                System.out.println("Transaction failed: "
                        + e.getMessage());
            } catch (ArithmeticException e) {
                System.out.println("Amount totals exceeded the supported range.");
            } catch (Exception e) {
                System.out.println("Operation failed: "
                        + e.getMessage());
            }
        }

        sc.close();
    }
}