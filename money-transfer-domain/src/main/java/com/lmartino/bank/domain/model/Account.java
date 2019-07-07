package com.lmartino.bank.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.lmartino.bank.domain.exception.DomainExceptionHandler.insufficientBalanceException;

/**
 * Aggregation class to represent a Bank Account
 */
@Getter
@Log
@ToString
@EqualsAndHashCode
public class Account {
    private Id id;
    private String name;
    private Amount balance;
    private LocalDateTime createdAt;

    private Account(final Id id, final String name, final Amount balance, final LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public static Account of(final Id id, final String name, final Amount balance, final LocalDateTime createdAt) {
        return new Account(id, name, balance, createdAt);
    }

    public static Account createNewAccount(final String name, final Amount balance) {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Account account = new Account(Id.create(), name, balance, now);
        log.info(String.format("Created new account %s", account));
        return account;
    }

    public void withdraw(Amount amount) {
        if (this.balance.isGreaterThan(amount))
            this.balance.decreaseBy(amount);
        else
            insufficientBalanceException(this.id.getValue());
    }

    public void deposit(Amount amount) {
        this.balance.increaseBy(amount);
    }
}
