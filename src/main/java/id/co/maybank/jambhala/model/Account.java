package id.co.maybank.jambhala.model;

import java.math.BigDecimal;

public class Account {
    private String accountNumber;
    private BigDecimal accountBalance;

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }
}
