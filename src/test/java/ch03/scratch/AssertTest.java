package ch03.scratch;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class AssertTest {
    class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message) {
            super(message);
        }

        private static final long serialVersionUID = 1L;
    }

    private class Account {

        private String accountName;

        private int balance;

        public Account(String accountName) {
            this.accountName = accountName;
        }

        public void deposit(int rupees) {
            balance += rupees;
        }

        public boolean hasPositiveBalance() {
            return balance > 0;
        }

        public int getBalance() {
            return balance;
        }

        private String getName() {
            return accountName;
        }

        private void withdraw(int rupees) {
            if (balance < rupees) {
                throw new InsufficientFundsException("balance only " + balance);
            }
            balance -= rupees;

        }
    }

    private Account account;

    @Before
    public void createAccount() {
        account = new Account("an account name");
    }

    @Test
    public void hasPositiveBalance() {
        account.deposit(50);
        assertTrue(account.hasPositiveBalance());
    }

    @Test
    public void depositIncreasesBalance() {
        int initialBalance = account.getBalance();
        account.deposit(100);
        assertTrue(account.getBalance() > initialBalance);
        assertThat(account.getBalance(), equalTo(100));
    }

    @Test
    public void depositIncreasesBalance_hamcrestAssertTrue() {
        account.deposit(50);
        assertThat(account.getBalance() > 0, is(true));
    }


    @Test
    @ExpectToFail
    @Ignore
    public void assertFailure() {
        assertTrue(account.getName().startsWith("xyz"));
    }

    @Ignore
    @ExpectToFail
    @Test
    public void comparesArraysFailing() {
        assertThat(new String[]{"a", "b", "c"}, equalTo(new String[]{"a", "b"}));
    }

    @Test
    public void comparesArraysPassing() {
        assertThat(new String[]{"a", "b"}, equalTo(new String[]{"a", "b"}));
    }

    @Ignore
    @ExpectToFail
    @Test
    public void comparesCollectionsFailing() {
        assertThat(Arrays.asList(new String[]{"a"}),
                equalTo(Arrays.asList(new String[]{"a", "ab"})));
    }

    @Test
    public void comparesCollectionsPassing() {
        assertThat(Arrays.asList(new String[]{"a"}),
                equalTo(Arrays.asList(new String[]{"a"})));
    }

    @Test
    public void variousMatcherTests() {
        Account account = new Account("my big fat acct");
        assertThat(account.getName(), is(equalTo("my big fat acct")));

        assertThat(account.getName(), allOf(startsWith("my"), endsWith("acct")));

        assertThat(account.getName(), anyOf(startsWith("my"), endsWith("loot")));

        assertThat(account.getName(), not(equalTo("plunderings")));

        assertThat(account.getName(), is(not(nullValue())));
        assertThat(account.getName(), is(notNullValue()));

        assertThat(account.getName(), isA(String.class));

        assertThat(account.getName(), is(notNullValue())); // not helpful
        assertThat(account.getName(), equalTo("my big fat acct"));
    }

    @Test
    public void testWithWorthlessAssertionComment() {
        account.deposit(50);
        assertThat("account balance is 100", account.getBalance(), equalTo(50));
    }


    @Test(expected = InsufficientFundsException.class)
    public void throwsWhenWithrowingTooMuch() {
        account.withdraw(100);
    }

    @Test
    public void throwsWhenWithdrawingTooMuchTry() {
        try {
            account.withdraw(100);
            fail();
        } catch (InsufficientFundsException expected) {
            assertThat(expected.getMessage(), equalTo("balance only 0"));
        }
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void exceptionRule() {
        thrown.expect(InsufficientFundsException.class);
        thrown.expectMessage("balance only 0");

        account.withdraw(100);
    }

    @Test
    public void readsFromTestFile() throws IOException {
        String filename = "test.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("test data");
        writer.close();
        // ...
    }
}
