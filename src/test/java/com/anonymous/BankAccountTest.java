package com.anonymous;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountTest {

    private static final double DELTA = 0.0001;
    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount();
    }

    @Test
    @DisplayName("Új számla egyenlege 0")
    void newAccountInitialBalanceIsZero() {
        assertEquals(0.0, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("deposit(1000) után az egyenleg 1000")
    void depositIncreasesBalanceToExactAmount() {
        account.deposit(1000.0);
        assertEquals(1000.0, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Több befizetés helyesen összeadódik")
    void multipleDepositsAccumulateCorrectly() {
        account.deposit(500.0);
        account.deposit(1500.0);
        account.deposit(250.50);
        assertEquals(2250.50, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Befizetés után a withdraw() helyesen csökkenti az egyenleget")
    void withdrawDecreasesBalanceCorrectly() {
        account.deposit(1000.0);
        account.withdraw(400.0);
        assertEquals(600.0, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("A teljes egyenleg felvehető")
    void withdrawFullBalanceLeavesZero() {
        account.deposit(1000.0);
        account.withdraw(1000.0);
        assertEquals(0.0, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Túl nagy összeg felvétele IllegalArgumentException kivételt dob")
    void withdrawAmountGreaterThanBalanceThrowsException() {
        account.deposit(1000.0);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(1500.0));
    }

    @Test
    @DisplayName("Negatív befizetés kivételt dob")
    void negativeDepositThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-100.0));
    }

    @Test
    @DisplayName("0 összegű befizetés kivételt dob")
    void zeroDepositThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0.0));
    }

    @Test
    @DisplayName("Negatív pénzfelvétel kivételt dob")
    void negativeWithdrawThrowsException() {
        account.deposit(1000.0);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-50.0));
    }

    @Test
    @DisplayName("0 összegű pénzfelvétel kivételt dob")
    void zeroWithdrawThrowsException() {
        account.deposit(1000.0);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0.0));
    }

    @Test
    @DisplayName("Sikertelen pénzfelvétel (túl nagy összeg) után az egyenleg változatlan marad")
    void balanceUnchangedAfterOverdrawAttempt() {
        account.deposit(1000.0);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(2000.0));
        assertEquals(1000.0, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Sikertelen érvénytelen befizetés után az egyenleg változatlan marad")
    void balanceUnchangedAfterInvalidDepositAttempts() {
        account.deposit(500.0);

        assertThrows(IllegalArgumentException.class, () -> account.deposit(-100.0));
        assertEquals(500.0, account.getBalance(), DELTA);

        assertThrows(IllegalArgumentException.class, () -> account.deposit(0.0));
        assertEquals(500.0, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Sikertelen érvénytelen pénzfelvétel (negatív vagy 0) után az egyenleg változatlan marad")
    void balanceUnchangedAfterInvalidWithdrawAttempts() {
        account.deposit(500.0);

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-50.0));
        assertEquals(500.0, account.getBalance(), DELTA);

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0.0));
        assertEquals(500.0, account.getBalance(), DELTA);
    }
}
