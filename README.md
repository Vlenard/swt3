# BankAccount - Egyszerű bankszámla projekt

Ez a projekt egy egyszerű bankszámlát (`BankAccount`) megvalósító Java Maven projekt JUnit 5 tesztekkel ellátva.

## Követelmények és funkcionalitás

A `BankAccount` osztály a következő funkciókat biztosítja:
- **Kezdőegyenleg:** Alapértelmezetten 0.
- **Befizetés (`deposit(double amount)`):**
  - Növeli az egyenleget.
  - 0 vagy negatív összeg esetén `IllegalArgumentException` kivételt dob.
- **Pénzfelvétel (`withdraw(double amount)`):**
  - Csökkenti az egyenleget.
  - 0 vagy negatív összeg esetén `IllegalArgumentException` kivételt dob.
  - Ha az összeg meghaladja a rendelkezésre álló egyenleget, `IllegalArgumentException` kivételt dob.
- **Egyenleg lekérdezése (`getBalance()`):**
  - Visszaadja az aktuális egyenleget.
- **Integritás:** Sikertelen művelet (kivétel dobása) esetén a számla egyenlege változatlan marad.

---

## Előfeltételek

- **Java JDK:** Java 27 (vagy Java 21/17 kompatibilis beállítással)
- **Apache Maven:** 3.8+ (vagy újabb)
- **Git**

---

## Projekt felépítése

```text
.
├── pom.xml
├── README.md
└── src
    ├── main
    │   └── java
    │       └── com
    │           └── anonymous
    │               └── BankAccount.java
    └── test
        └── java
            └── com
                └── anonymous
                    └── BankAccountTest.java
```

---

## Futtatás és tesztelés

A projekt Maven segítségével fordítható és tesztelhető.

### 1. Projekt fordítása
```bash
mvn clean compile
```

### 2. JUnit 5 egységtesztek futtatása
```bash
mvn test
```

### 3. Csomagolás (JAR fájl készítése tesztek futtatásával)
```bash
mvn clean package
```

---

## Tesztesetek lefedettsége

A JUnit 5 tesztosztály (`BankAccountTest`) ellenőrzi:
1. Új számla induló egyenlege 0.
2. `deposit(1000)` után az egyenleg pontosan 1000.
3. Több egymást követő befizetés helyesen összeadódik.
4. Befizetés után a `withdraw()` helyesen csökkenti az egyenleget.
5. A teljes számlaegyenleg felvehető (az egyenleg 0 lesz).
6. Túl nagy összeg felvétele esetén `IllegalArgumentException` keletkezik.
7. Negatív befizetés esetén kivétel keletkezik.
8. 0 összegű befizetés esetén kivétel keletkezik.
9. Negatív pénzfelvétel esetén kivétel keletkezik.
10. 0 összegű pénzfelvétel esetén kivétel keletkezik.
11. Sikertelen műveletek (túl nagy felvétel, negatív/0 befizetés, negatív/0 felvétel) után az egyenleg garantáltan változatlan marad.
