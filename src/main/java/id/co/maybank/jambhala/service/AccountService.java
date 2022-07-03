package id.co.maybank.jambhala.service;

import id.co.maybank.jambhala.entity.AccountBalanceEsb;
import id.co.maybank.jambhala.model.AccountBalance;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    AccountBalanceEsb accountBalanceEsb;

    public AccountService(AccountBalanceEsb accountBalanceEsb) {
        this.accountBalanceEsb = accountBalanceEsb;
    }

    public AccountBalance getBalance(String username, String accountNumber) {
        return accountBalanceEsb.getBalance(username, accountNumber);
    }
}
