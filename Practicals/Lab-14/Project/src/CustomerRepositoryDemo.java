import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

import java.util.ArrayList;

import service.Repository;

class Customer {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Id {
    }

    @Id
    private String customerId;

    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{customerId='" + customerId
                + "', name='" + name + "'}";
    }
}

public class CustomerRepositoryDemo {

    public static void main(String[] args) {

        Repository<Customer> repository = new Repository<>();

        Customer c1 = new Customer("C001", "Aarav");
        Customer c2 = new Customer("C002", "Diya");
        Customer c3 = new Customer("C003", "Krish");

        repository.saveAll(c1, c2, c3);

        System.out.println("===== CUSTOMER REPOSITORY =====");

        System.out.println("\nCustomer found:");
        System.out.println(repository.findById("C002"));

        System.out.println("\nAll customers:");

        ArrayList<Customer> customers = repository.findAll();

        for (Customer customer : customers) {
            System.out.println(customer);
        }

        System.out.println("\nTotal customers: " + repository.count());
    }
}