# BankAccount & BankService (JUnit 5 + Mockito)

Ez a projekt egy egyszerű bankszámlát (`BankAccount`), banki szolgáltatást (`BankService`), értesítési szolgáltatást (`NotificationService`) és árfolyam-szolgáltatást (`ExchangeRateService`) megvalósító Java Maven projekt **JUnit 5** és **Mockito** tesztekkel.

---

## Modulok és felépítés

### 1. `BankAccount`
- **Kezdőegyenleg:** Alapértelmezetten `0.0`.
- **`deposit(double amount)`:** Növeli az egyenleget. 0 vagy negatív összeg esetén `IllegalArgumentException` kivételt dob.
- **`withdraw(double amount)`:** Csökkenti az egyenleget. 0, negatív vagy fedezethiány esetén `IllegalArgumentException` kivételt dob.
- **`getBalance()`:** Visszaadja az aktuális egyenleget.
- **Integritás:** Sikertelen művelet esetén az egyenleg változatlan marad.

### 2. `NotificationService` (interfész)
- Külső értesítési szolgáltatás absztrakciója.
- `void send(String message)` metódussal küld üzenetet a felhasználónak.

### 3. `ExchangeRateService` (interfész)
- Devizaárfolyam-lekérdező szolgáltatás absztrakciója.
- `double getRate(String fromCurrency, String toCurrency)` metódussal kérdezi le az árfolyamot.

### 4. `BankService`
- Üzleti logikai réteg, amely a függőségeit konstruktoron keresztül kapja (**Dependency Injection**).
- **`deposit(BankAccount account, double amount)`:**
  - Végrehajtja a számlára a befizetést (`account.deposit(amount)`).
  - Sikeres befizetés esetén értesítést küld a `NotificationService.send()` hívásával.
  - Sikertelen befizetés (kivétel) esetén **nem történik értesítés**.
- **`depositForeignCurrency(BankAccount account, double amount, String fromCurrency, String toCurrency)`:**
  - Átváltja az összeget az `ExchangeRateService` segítségével, majd jóváírja és értesítést küld.

---

## Projekt struktúra

```text
.
├── pom.xml
├── README.md
└── src
    ├── main
    │   └── java
    │       └── com
    │           └── anonymous
    │               ├── BankAccount.java
    │               ├── BankService.java
    │               ├── ExchangeRateService.java
    │               └── NotificationService.java
    └── test
        └── java
            └── com
                └── anonymous
                    ├── BankAccountTest.java
                    ├── BankServiceTest.java
                    └── ExchangeRateServiceTest.java
```

---

## Mockito funkciók bemutatása a tesztekben

A tesztek során mockoljuk a külső függőségeket, így nem szükséges valódi értesítési vagy árfolyam-szolgáltatás megléte:

1. **`mock()`:** Mock példányok létrehozása:
   ```java
   NotificationService notificationService = mock(NotificationService.class);
   ExchangeRateService exchangeRateService = mock(ExchangeRateService.class);
   ```
2. **`when(...).thenReturn(...)`:** Mock viselkedés előzetes konfigurálása:
   ```java
   when(exchangeRateService.getRate("EUR", "HUF")).thenReturn(400.0);
   ```
3. **`verify()`:** Interakciók és paraméterek ellenőrzése:
   ```java
   // Ellenőrzi, hogy a várt üzenettel hívódott-e meg a send()
   verify(notificationService).send("Sikeres befizetés: 1000.0");

   // Ellenőrzi, hogy sikertelen tranzakció esetén soha nem hívódott meg
   verify(notificationService, never()).send(anyString());
   ```

---

## Futtatás és tesztelés

A projekt Java 27 (vagy Java 17/21) és Maven használatával fordítható.

### Tesztek futtatása:
```bash
mvn clean test
```

### Projekt fordítása és csomagolása:
```bash
mvn clean package
```

---

## Git ágak

- `main`: Az alap `BankAccount` osztály és JUnit 5 alaptesztek.
- `feature/mockito`: A Mockito bővítés (`NotificationService`, `BankService`, `ExchangeRateService`, mock tesztek).
