public class MiniBank {

    public static void main(String[] args) {

        // Create different account types
        Account[] accounts = {
            new SavingsAccount("Riya", 10000, 2000),
            new CurrentAccount("Priya", 5000, 3000),
            new FixedDepositAccount("Aarav", 20000)
        };

        System.out.println("================================");
        System.out.println("       MINIBANK ACCOUNTS");
        System.out.println("================================");

        // Runtime polymorphism
        for (Account account : accounts) {

            System.out.println("\n" + account);

            System.out.println(
                    "Interest Rate: "
                    + account.interestRate() + "%"
            );

            System.out.printf(
                    "Monthly Interest: Rs. %.2f%n",
                    account.monthlyInterest()
            );

            // Pattern matching with instanceof
            if (account instanceof SavingsAccount savings) {
                System.out.println(
                        "Minimum Balance: Rs. "
                        + savings.getMinBalance()
                );
            }

            boolean result = account.withdraw(1000);

            System.out.println(
                    "Withdraw Rs. 1000: " + result
            );

            System.out.println(
                    "Balance After Withdrawal: Rs. "
                    + account.getBalance()
            );
        }

        System.out.println("\n================================");
        System.out.println("          END OF PROGRAM");
        System.out.println("================================");
    }
}