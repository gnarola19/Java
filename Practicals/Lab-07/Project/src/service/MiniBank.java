package service;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.Premium;
import util.WithdrawRule;

import static util.Validator.isValidAmount;

public class MiniBank {

    public static void main(String[] args) {

        Account[] accounts = {
            new SavingsAccount("Riya", 10000, 2000),
            new CurrentAccount("Priya", 5000, 3000),
            new FixedDepositAccount("Aarav", 20000)
        };

        System.out.println("================================");
        System.out.println("       MINIBANK ACCOUNTS");
        System.out.println("================================");

        for (Account account : accounts) {

            System.out.println("\n" + account);

            System.out.println(
                    "Interest Rate: " + account.interestRate() + "%"
            );

            System.out.printf(
                    "Monthly Interest: Rs. %.2f%n",
                    account.monthlyInterest()
            );

            System.out.printf(
                    "Yearly Interest: Rs. %.2f%n",
                    account.yearlyInterest()
            );

            if (account instanceof SavingsAccount savings) {
                System.out.println(
                        "Minimum Balance: Rs. " + savings.getMinBalance()
                );
            }

            boolean result = account.withdraw(1000);

            System.out.println("Withdraw Rs. 1000: " + result);
            System.out.println("Balance After Withdrawal: Rs. " + account.getBalance());
        }

        // Functional interface using an anonymous class
        WithdrawRule rule1 = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return amount > 0 && account.getBalance() >= amount;
            }
        };

        // Functional interface using a lambda expression
        WithdrawRule rule2 = (account, amount) ->
                amount > 0 && account.getBalance() >= amount;

        Account testAccount = new SavingsAccount("Test", 5000, 1000);

        System.out.println("\n================================");
        System.out.println("       WITHDRAW RULE TEST");
        System.out.println("================================");

        System.out.println(
                "Anonymous Class: " + rule1.allow(testAccount, 2000)
        );

        System.out.println(
                "Lambda Expression: " + rule2.allow(testAccount, 2000)
        );

        // Static import demonstration
        System.out.println(
                "Valid Amount (500): " + isValidAmount("500")
        );

        // Marker interface demonstration
        Premium premiumCustomer = new Premium() {};

        System.out.println(
                "Premium Marker Interface: " +
                (premiumCustomer instanceof Premium)
        );

        System.out.println("\n================================");
        System.out.println("          END OF PROGRAM");
        System.out.println("================================");
    }
}