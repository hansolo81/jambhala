package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.model.AccountBalance;
import id.co.rimaubank.jambhala.model.AccountHolder;
import id.co.rimaubank.jambhala.model.EsbAccountInfoRes;
import id.co.rimaubank.jambhala.service.esb.AccountInfoESB;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    AccountInfoESB accountInfoESB;

    public AccountService(AccountInfoESB accountInfoESB) {
        this.accountInfoESB = accountInfoESB;
    }

    public AccountBalance getBalance(String customerNumber, String accountNumber) {
        EsbAccountInfoRes esbAccountInfo = accountInfoESB.getAccountInfo(customerNumber, accountNumber);
        return new AccountBalance(esbAccountInfo.accountNumber(), esbAccountInfo.availableBalance());
    }

    public AccountHolder getAccountHolder(String customerNumber, String accountNumber) {
        EsbAccountInfoRes esbAccountInfo = accountInfoESB.getAccountInfo(customerNumber, accountNumber);
        return new AccountHolder(esbAccountInfo.accountNumber(), esbAccountInfo.holderName());
    }
}
