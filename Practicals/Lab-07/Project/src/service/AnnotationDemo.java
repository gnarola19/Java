package service;

import model.Account;
import util.AnnotationValidator;

class TestAccount extends Account {
    public TestAccount(String name, long balance) {
        super(name, balance);
    }

    @Override
    public double interestRate() {
        return 4.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return amount > 0 && balance >= amount;
    }
}

public class AnnotationDemo {
    public static void main(String[] args) {
        Account invalid = new TestAccount("Riya", -100);

        System.out.println("Invalid Account (-100):");
        String[] errors = AnnotationValidator.validate(invalid);

        for (String error : errors) {
            System.out.println(error);
        }

        Account valid = new TestAccount("Priya", 1000);

        System.out.println("\nValid Account (1000):");
        String[] validErrors = AnnotationValidator.validate(valid);

        if (validErrors.length == 0) {
            System.out.println("No validation errors");
        } else {
            for (String error : validErrors) {
                System.out.println(error);
            }
        }
    }
}