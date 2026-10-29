import model.Account;
import model.SavingsAccount;
import service.BankService;
import service.Repository;
import service.TransactionProcessor;
import exception.InvalidAmountException;
import exception.InsufficientFundsException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankServiceTest {

    @Test
    void depositIncreasesBalance() throws Exception {
        Repository<Account> repository = new Repository<>();
        BankService bank = new BankService(repository);

        Account account = new SavingsAccount("Test User", 1000, 0);
        bank.openAccount(account);

        bank.deposit(account.getAccountNumber(), 500);

        assertEquals(1500, account.getBalance());
    }

    @Test
    void withdrawalBeyondAvailableBalanceFails() throws Exception {
        Repository<Account> repository = new Repository<>();
        BankService bank = new BankService(repository);

        Account account = new SavingsAccount("Test User", 1000, 500);
        bank.openAccount(account);

        assertThrows(
            InsufficientFundsException.class,
            () -> bank.withdraw(account.getAccountNumber(), 600)
        );
    }

    @Test
    void concurrentDepositsPreserveExpectedBalance()
            throws Exception {
        Account account = new SavingsAccount("Concurrent User", 1000, 0);
        TransactionProcessor processor = new TransactionProcessor();
        processor.registerAccount(account);

        int count = 100;
        for (int i = 0; i < count; i++) {
            processor.submitDeposit(account.getAccountNumber(), 10);
        }

        processor.stop();

        assertEquals(2000, account.getBalance());
    }
}