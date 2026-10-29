public class MiniBank {

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("       WELCOME TO MINIBANK");
        System.out.println("================================");

        Account[] accounts = {
            new Account("Riya", 5000),
            new Account("Priya", 3000),
            new Account("Aarav")
        };

        // Deposit
        accounts[0].deposit(2000);

        System.out.println("\n--- Deposit ---");
        System.out.println("Balance after deposit: "
                + accounts[0].getBalance());

        // Successful withdrawal
        boolean result1 = accounts[0].withdraw(3000);

        System.out.println("\n--- Withdrawal 1 ---");
        System.out.println("Successful: " + result1);
        System.out.println("Balance: "
                + accounts[0].getBalance());

        // Failed withdrawal
        boolean result2 = accounts[0].withdraw(10000);

        System.out.println("\n--- Withdrawal 2 ---");
        System.out.println("Successful: " + result2);
        System.out.println("Balance: "
                + accounts[0].getBalance());

        // Display all accounts
        System.out.println("\n================================");
        System.out.println("         ACCOUNT DETAILS");
        System.out.println("================================");

        for (Account account : accounts) {
            System.out.println("\nAccount Number: "
                    + account.getAccountNumber());
            System.out.println("Owner: "
                    + account.getOwnerName());
            System.out.println("Balance: Rs. "
                    + account.getBalance());
            System.out.println("Active: "
                    + account.isActive());
        }

        System.out.println("\n================================");
        System.out.println("       THANK YOU!");
        System.out.println("================================");
    }
}