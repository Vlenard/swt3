package com.anonymous;

/**
 * Egyszerű bankszámlát reprezentáló osztály.
 */
public class BankAccount {

    private double balance;

    /**
     * Létrehoz egy új bankszámlát 0 kezdőegyenleggel.
     */
    public BankAccount() {
        this.balance = 0.0;
    }

    /**
     * Összeg befizetése a számlára.
     *
     * @param amount a befizetendő összeg (pozitív számnak kell lennie)
     * @throws IllegalArgumentException ha az összeg 0 vagy negatív
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("A befizetendő összegnek pozitívnak kell lennie: " + amount);
        }
        this.balance += amount;
    }

    /**
     * Összeg kifizetése / levonása a számláról.
     *
     * @param amount a felvenni kívánt összeg (pozitív számnak kell lennie)
     * @throws IllegalArgumentException ha az összeg 0 vagy negatív, vagy meghaladja az egyenleget
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("A felvenni kívánt összegnek pozitívnak kell lennie: " + amount);
        }
        if (amount > this.balance) {
            throw new IllegalArgumentException(
                    "Nincs elegendő fedezet. Aktuális egyenleg: " + this.balance + ", kért összeg: " + amount);
        }
        this.balance -= amount;
    }

    /**
     * Visszaadja az aktuális egyenleget.
     *
     * @return az aktuális egyenleg
     */
    public double getBalance() {
        return this.balance;
    }
}
