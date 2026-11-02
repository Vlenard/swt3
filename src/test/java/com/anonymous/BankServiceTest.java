package com.anonymous;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BankServiceTest {

    private static final double DELTA = 0.0001;

    private NotificationService notificationService;
    private ExchangeRateService exchangeRateService;
    private BankService bankService;
    private BankAccount account;

    @BeforeEach
    void setUp() {
        // Mock objektumok létrehozása a mock() függvénnyel
        notificationService = mock(NotificationService.class);
        exchangeRateService = mock(ExchangeRateService.class);

        // Dependency Injection: mock objektumok átadása a konstruktornak
        bankService = new BankService(notificationService, exchangeRateService);
        account = new BankAccount();
    }

    @Test
    @DisplayName("A befizetés megfelelően növeli a számla egyenlegét")
    void depositIncreasesAccountBalance() {
        bankService.deposit(account, 1500.0);

        assertEquals(1500.0, account.getBalance(), DELTA);
    }

    @Test
    @DisplayName("Sikeres befizetés után meghívódik a NotificationService.send() metódusa")
    void successfulDepositCallsNotificationServiceSend() {
        bankService.deposit(account, 1000.0);

        verify(notificationService, times(1)).send(anyString());
    }

    @Test
    @DisplayName("A verify() segítségével ellenőrizd a megfelelő értesítés elküldését")
    void verifyExactNotificationSentOnDeposit() {
        bankService.deposit(account, 2500.0);

        verify(notificationService).send("Sikeres befizetés: 2500.0");
    }

    @Test
    @DisplayName("Sikertelen befizetés esetén nem történik értesítés (negatív összeg)")
    void negativeDepositDoesNotTriggerNotification() {
        assertThrows(IllegalArgumentException.class, () -> bankService.deposit(account, -500.0));

        // Az egyenleg nem változik
        assertEquals(0.0, account.getBalance(), DELTA);
        // Értesítés nem került elküldésre
        verify(notificationService, never()).send(anyString());
        verifyNoInteractions(notificationService);
    }

    @Test
    @DisplayName("Sikertelen befizetés esetén nem történik értesítés (0 összeg)")
    void zeroDepositDoesNotTriggerNotification() {
        assertThrows(IllegalArgumentException.class, () -> bankService.deposit(account, 0.0));

        assertEquals(0.0, account.getBalance(), DELTA);
        verify(notificationService, never()).send(anyString());
        verifyNoInteractions(notificationService);
    }

    @Test
    @DisplayName("Deviza befizetés árfolyam átváltással when().thenReturn() segítségével")
    void depositForeignCurrencyUsesMockedExchangeRate() {
        // Mock viselkedés beállítása when().thenReturn() használatával
        when(exchangeRateService.getRate("EUR", "HUF")).thenReturn(400.0);

        bankService.depositForeignCurrency(account, 100.0, "EUR", "HUF");

        // 100 EUR * 400.0 = 40 000 HUF
        assertEquals(40000.0, account.getBalance(), DELTA);

        // Hívások ellenőrzése verify() használatával
        verify(exchangeRateService).getRate("EUR", "HUF");
        verify(notificationService).send("Sikeres befizetés: 40000.0");
    }
}
