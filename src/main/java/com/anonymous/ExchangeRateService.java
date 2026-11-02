package com.anonymous;

/**
 * Valutaárfolyam lekérdezését biztosító szolgáltatás interfész.
 */
public interface ExchangeRateService {

    /**
     * Visszaadja a megadott devizapár árfolyamát.
     *
     * @param fromCurrency forrás deviza (pl. "EUR")
     * @param toCurrency   cél deviza (pl. "HUF")
     * @return az átváltási árfolyam
     */
    double getRate(String fromCurrency, String toCurrency);

    /**
     * Kényelmi alias metódus az árfolyam lekérdezésére.
     *
     * @param fromCurrency forrás deviza
     * @param toCurrency   cél deviza
     * @return az átváltási árfolyam
     */
    default double getExchangeRate(String fromCurrency, String toCurrency) {
        return getRate(fromCurrency, toCurrency);
    }
}
