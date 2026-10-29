public class MiniBank {

    public static void main(String[] args) {

        System.out.println("===== MINIBANK =====");

        // Create three accounts
        Account a1 = new Account("Riya", 5000);
        Account a2 = new Account("Priya", 3000);
        Account a3 = new Account("Aarav");

        // Deposit and withdraw
        a1.deposit(2000);
        a1.withdraw(3000);

        Account[] accounts = {a1, a2, a3};

        // Display account details
        System.out.println("\n--- Account Details ---");

        for (Account account : accounts) {
            System.out.println(account);
        }

        // Test equals() and hashCode()
        System.out.println("\n--- Equality Test ---");

        Account a4 = new Account("AC0001", "Riya", 4000);

        System.out.println("a1 equals a4: " + a1.equals(a4));
        System.out.println("Hash codes equal: "
                + (a1.hashCode() == a4.hashCode()));

        // Test instanceof
        System.out.println("\n--- instanceof Test ---");

        Object obj = a1;
        System.out.println("Is Account: " + (obj instanceof Account));

        // Create customer with address
        Customer.Address address = new Customer.Address(
                "Nisarg Hostel", "Anand", "388001");

        Customer customer = new Customer(
                "Riya", "riya@example.com", "9876543210", address);

        System.out.println("\n--- Customer Details ---");
        System.out.println("Customer ID: " + customer.getCustomerId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Mobile: " + customer.getMobile());
        System.out.println("Address: " + customer.getAddress().getLine());
        System.out.println("City: " + customer.getAddress().getCity());
        System.out.println("Pincode: " + customer.getAddress().getPincode());

        // Test cloning
        Customer copy = customer.clone();

        System.out.println("\n--- Clone Test ---");
        System.out.println("Original: " + customer.getName());
        System.out.println("Cloned: " + copy.getName());
        System.out.println("Different objects: " + (customer != copy));
    }
}