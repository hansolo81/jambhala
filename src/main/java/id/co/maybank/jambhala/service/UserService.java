package id.co.maybank.jambhala.service;

import id.co.maybank.jambhala.model.Account;
import id.co.maybank.jambhala.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    public User getUser(String userName) {
        User user = new User(userName);
        Account account = new Account();
        account.setAccountNumber("66666666");
        account.setAccountBalance(new BigDecimal("10001.00"));
        user.setAccounts(List.of(account));
        return user;
    }
}
