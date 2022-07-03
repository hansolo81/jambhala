package id.co.maybank.jambhala.service;

import id.co.maybank.jambhala.entity.AccountBalanceEsb;
import id.co.maybank.jambhala.model.AccountBalance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceShould {

    private AccountService accountService;

    @Mock
    AccountBalanceEsb accountBalanceEsb;
    String username;
    String accountNumber;

    @Before
    public void initialise() {
        username = "anakin";
        accountNumber = "10000000066";
        accountService = new AccountService(accountBalanceEsb);
    }

    @Test
    public void returnAccountBalance() {
        AccountBalance expected = new AccountBalance(accountNumber, BigDecimal.valueOf(10001));
        given(accountBalanceEsb.getBalance(username, accountNumber))
                .willReturn(expected);

        AccountBalance actual = accountService.getBalance(username, accountNumber);
        assertThat(actual, is(expected));
    }

}