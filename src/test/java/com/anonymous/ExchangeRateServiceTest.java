package com.anonymous;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExchangeRateServiceTest {

    private static final double DELTA = 0.0001;

    @Test
    @DisplayName("Mock ExchangeRateService viselkedés beállítása when(...).thenReturn(...) segítségével")
    void testExchangeRateMockWithWhenThenReturn() {
        // 1. Mock objektum létrehozása a mock() metódussal
        ExchangeRateService exchangeRateService = mock(ExchangeRateService.class);

        // 2. Viselkedés beállítása a when(...).thenReturn(...) metódussal
        when(exchangeRateService.getRate("EUR", "HUF")).thenReturn(395.50);
        when(exchangeRateService.getRate("USD", "HUF")).thenReturn(365.25);
        when(exchangeRateService.getRate("GBP", "HUF")).thenReturn(470.00);

        // 3. A beállított válasz ellenőrzése
        assertEquals(395.50, exchangeRateService.getRate("EUR", "HUF"), DELTA);
        assertEquals(365.25, exchangeRateService.getRate("USD", "HUF"), DELTA);
        assertEquals(470.00, exchangeRateService.getRate("GBP", "HUF"), DELTA);

        // 4. Interakciók ellenőrzése verify() segítségével
        verify(exchangeRateService).getRate("EUR", "HUF");
        verify(exchangeRateService).getRate("USD", "HUF");
        verify(exchangeRateService).getRate("GBP", "HUF");
    }

    @Test
    @DisplayName("Különböző devizákra eltérő árfolyam adható vissza mock segítségével")
    void testDynamicMockedExchangeRates() {
        ExchangeRateService exchangeRateService = mock(ExchangeRateService.class);

        when(exchangeRateService.getRate("CHF", "HUF")).thenReturn(415.0);

        double rate = exchangeRateService.getRate("CHF", "HUF");
        assertEquals(415.0, rate, DELTA);

        verify(exchangeRateService, times(1)).getRate("CHF", "HUF");
    }
}
