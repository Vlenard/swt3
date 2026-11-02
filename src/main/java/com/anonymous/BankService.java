package com.anonymous;

/**
 * Banki szolgáltatásokat megvalósító osztály, amely függőségeit
 * konstruktoron keresztül kapja (Dependency Injection).
 */
public class BankService {

    private final NotificationService notificationService;
    private final ExchangeRateService exchangeRateService;

    /**
     * Konstruktor Dependency Injection-nel értesítési szolgáltatáshoz.
     *
     * @param notificationService az értesítési szolgáltatás
     */
    public BankService(NotificationService notificationService) {
        this(notificationService, null);
    }

    /**
     * Bővített konstruktor értesítési és árfolyam-szolgáltatással.
     *
     * @param notificationService az értesítési szolgáltatás
     * @param exchangeRateService az árfolyam-szolgáltatás
     */
    public BankService(NotificationService notificationService, ExchangeRateService exchangeRateService) {
        if (notificationService == null) {
            throw new IllegalArgumentException("A NotificationService nem lehet null");
        }
        this.notificationService = notificationService;
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * Befizetés végrehajtása a bankszámlára, majd értesítés küldése.
     * Sikertelen befizetés esetén kivétel váltódik ki, és nem történik értesítés.
     *
     * @param account a cél bankszámla
     * @param amount  a befizetendő összeg
     */
    public void deposit(BankAccount account, double amount) {
        if (account == null) {
            throw new IllegalArgumentException("A BankAccount nem lehet null");
        }
        account.deposit(amount);
        notificationService.send("Sikeres befizetés: " + amount);
    }

    /**
     * Külföldi deviza befizetése átváltással.
     *
     * @param account      a cél bankszámla
     * @param amount       a deviza összege
     * @param fromCurrency forrás deviza
     * @param toCurrency   cél deviza
     */
    public void depositForeignCurrency(BankAccount account, double amount, String fromCurrency, String toCurrency) {
        if (exchangeRateService == null) {
            throw new IllegalStateException("ExchangeRateService nincs konfigurálva");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("A befizetendő összegnek pozitívnak kell lennie: " + amount);
        }
        double rate = exchangeRateService.getRate(fromCurrency, toCurrency);
        double convertedAmount = amount * rate;
        deposit(account, convertedAmount);
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public ExchangeRateService getExchangeRateService() {
        return exchangeRateService;
    }
}
