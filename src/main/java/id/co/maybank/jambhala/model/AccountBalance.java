package id.co.maybank.jambhala.model;

import java.math.BigDecimal;

public class AccountBalance {
    private String accountNumber;
    private BigDecimal availableBalance;
    private BigDecimal holdAmount;
    private BigDecimal floatAmount;

    public AccountBalance(String accountNumber, BigDecimal availableBalance) {
        this.accountNumber = accountNumber;
        this.availableBalance = availableBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getHoldAmount() {
        return holdAmount;
    }

    public void setHoldAmount(BigDecimal holdAmount) {
        this.holdAmount = holdAmount;
    }

    public BigDecimal getFloatAmount() {
        return floatAmount;
    }

    public void setFloatAmount(BigDecimal floatAmount) {
        this.floatAmount = floatAmount;
    }
}
