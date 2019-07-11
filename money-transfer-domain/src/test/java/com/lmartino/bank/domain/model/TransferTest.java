package com.lmartino.bank.domain.model;

import com.lmartino.bank.domain.exception.InsufficientBalanceException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TransferTest {

    private Currency eur = Currency.of("EUR");

    @Test
    public void givenEURFooAccountAndEURBarAccount_whenMakeTransferFromFooToBar_thenAmountIsIncreasedInEURInBarAndDecreasedInEURInFoo(){
        // Given foo and bar accounts
        BigDecimal initialFooBalance = BigDecimal.valueOf(2500.00);
        Account foo = Account.createNewAccount("Foo", Money.of(initialFooBalance, eur));
        BigDecimal initialBarBalance = BigDecimal.valueOf(1345.98);
        Account bar = Account.createNewAccount("Bar", Money.of(initialBarBalance, eur));

        // When Make transfer from foo to bar of 35.99 euros
        BigDecimal transferAmount = BigDecimal.valueOf(35.99);
        Transfer transfer = Transfer.makeTransfer(foo, bar, Money.of(transferAmount, eur), "Test Transfer", BigDecimal.ONE);

        // Then transfer amount is increased in Bar and decreased in Foo
        Assert.assertThat(transfer, is(notNullValue()));
        Assert.assertThat(foo.getBalance().getValue(), is(initialFooBalance.subtract(transferAmount)));
        Assert.assertThat(bar.getBalance().getValue(), is(initialBarBalance.add(transferAmount)));

    }

    @Test(expected = InsufficientBalanceException.class)
    public void givenFooAccountAndBarAccount_whenMakeTransferFromFooToBarAndFooHasNotSufficientBalance_thenTransferIsRejected(){
        // Given foo and bar accounts
        BigDecimal initialFooBalance = BigDecimal.valueOf(2500);
        Account foo = Account.createNewAccount("Foo", Money.of(initialFooBalance, eur));
        BigDecimal initialBarBalance = BigDecimal.valueOf(1345.98);
        Account bar = Account.createNewAccount("Bar", Money.of(initialBarBalance, eur));

        // When Make transfer from foo to bar of 35.99 euros
        BigDecimal transferAmount = BigDecimal.valueOf(3000);
        Transfer.makeTransfer(foo, bar, Money.of(transferAmount, eur), "Test Transfer", BigDecimal.ONE);

    }

    @Test
    public void givenEURFooAccountAndGBPBarAccount_whenMakeTransferFromFooToBar_thenAmountIsIncreasedInGBPInBarAndDecreasedInEURInFoo(){
        // Given foo and bar accounts
        BigDecimal initialFooBalance = BigDecimal.valueOf(2500.00);
        Account foo = Account.createNewAccount("Foo", Money.of(initialFooBalance, eur));
        BigDecimal initialBarBalance = BigDecimal.valueOf(1345.98);
        Account bar = Account.createNewAccount("Bar", Money.of(initialBarBalance, eur));

        // When Make transfer from foo to bar of 35.99 euros
        BigDecimal transferAmount = BigDecimal.valueOf(35.99);
        BigDecimal rate = BigDecimal.valueOf(0.899196);
        Transfer transfer = Transfer.makeTransfer(foo, bar, Money.of(transferAmount, eur), "Test Transfer", rate);

        // Then transfer amount is increased in Bar and decreased in Foo
        Assert.assertThat(transfer, is(notNullValue()));
        Assert.assertThat(foo.getBalance().getValue(), is(initialFooBalance.subtract(transferAmount)));
        Assert.assertThat(bar.getBalance().getValue(), is(initialBarBalance.add(transferAmount.multiply(rate)).setScale(2, RoundingMode.CEILING)));

    }

}