package service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;

public class MiniBank {

    public static void main(String[] args) {
        Path dataFolder = Paths.get("data");
        Path accountsFile = dataFolder.resolve("accounts.dat");
        Path logsFolder = dataFolder.resolve("logs");
        Path transactionFile = logsFolder.resolve("transactions.log");
        Path reportFile = dataFolder.resolve("end-of-day-report.txt");

        try {
            Files.createDirectories(logsFolder);

            System.out.println("===== MINIBANK PERSISTENCE =====");

            Account[] accounts = {
                new SavingsAccount("Aarav", 5000, 500),
                new CurrentAccount("Diya", 8000, 2000),
                new FixedDepositAccount("Krish", 10000)
            };

            // Save accounts to a binary file.
            StatePersister.save(accounts, accountsFile);

            // Load the saved accounts into a new array.
            Account[] loadedAccounts = StatePersister.load(accountsFile);

            System.out.println("\nReloaded accounts:");

            for (Account account : loadedAccounts) {
                System.out.println(account);
            }

            // Add sample transactions to the append-only log.
            TransactionLog.append(transactionFile, "DEPOSIT AC0001 500");
            TransactionLog.append(transactionFile, "WITHDRAW AC0002 200");
            TransactionLog.append(transactionFile, "DEPOSIT AC0003 1000");
            TransactionLog.append(transactionFile, "WITHDRAW AC0001 100");

            // Generate the end-of-day report.
            ReportGenerator.generate(logsFolder, reportFile);

            System.out.println("\n===== REPORT FILE CONTENTS =====");
            Files.readAllLines(reportFile).forEach(System.out::println);

            System.out.println("\nAll persistence and reporting tasks completed.");

        } catch (Exception e) {
            System.out.println("MiniBank error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}