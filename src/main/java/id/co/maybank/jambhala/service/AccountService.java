package id.co.maybank.jambhala.service;

import id.co.maybank.jambhala.entity.AccountInfoEsb;
import id.co.maybank.jambhala.mapper.ESBConverter;
import id.co.maybank.jambhala.model.AccountBalance;
import id.co.maybank.jambhala.model.AccountHolder;
import id.co.maybank.jambhala.model.EsbAccountInfoRes;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    AccountInfoEsb accountInfoEsb;

    public AccountService(AccountInfoEsb accountInfoEsb) {
        this.accountInfoEsb = accountInfoEsb;
    }

    public AccountBalance getBalance(String pan, String accountNumber) {
        EsbAccountInfoRes esbBalance = accountInfoEsb.getAccountInfo(pan, accountNumber);
        return ESBConverter.MAPPER.convertToAccountBalance(esbBalance);
    }

    @Cacheable("accountNumber")
    public AccountHolder getAccountHolder(String pan, String accountNumber) {
        EsbAccountInfoRes esbAccountHolder = accountInfoEsb.getAccountInfo(pan, accountNumber);
        return ESBConverter.MAPPER.convertToAccountHolder(esbAccountHolder) ;
    }
}
