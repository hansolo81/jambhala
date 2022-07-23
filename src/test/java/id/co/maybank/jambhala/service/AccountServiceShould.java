package id.co.maybank.jambhala.service;

import id.co.maybank.jambhala.entity.AccountInfoEsb;
import id.co.maybank.jambhala.model.AccountBalance;
import id.co.maybank.jambhala.model.EsbAccountInfoRes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceShould {

    private AccountService accountService;

    @Mock
    AccountInfoEsb accountInfoEsb;
    String username;
    String accountNumber;

    @Before
    public void initialise() {
        username = "anakin";
        accountNumber = "10000000066";
        accountService = new AccountService(accountInfoEsb);
    }

    @Test
    public void returnAccountBalance() {
        AccountBalance expected = new AccountBalance(accountNumber, BigDecimal.valueOf(10001));

        given(accountInfoEsb.getAccountInfo(username, accountNumber))
                .willReturn(EsbAccountInfoRes.builder()
                        .accountNumber(accountNumber)
                        .availableBalance(BigDecimal.valueOf(10001)).build());

        AccountBalance actual = accountService.getBalance(username, accountNumber);
        assertThat(actual.getAvailableBalance()).isEqualTo(expected.getAvailableBalance());
        assertThat(actual.getAccountNumber()).isEqualTo(expected.getAccountNumber());
    }

}