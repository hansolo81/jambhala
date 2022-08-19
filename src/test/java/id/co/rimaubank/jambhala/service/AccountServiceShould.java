package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.model.AccountBalance;
import id.co.rimaubank.jambhala.model.EsbAccountInfoRes;
import org.junit.Before;
import org.junit.BeforeClass;
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

    String accountNumber;

    @Mock
    AccountInfoESB esb;

    @Before
    public void initialize() {
       accountService = new AccountService(esb);
    }

    @Test
    public void returnAccountBalance() {
        BigDecimal expectedBalance = BigDecimal.valueOf(10001.00);
        given(esb.getAccountInfo(accountNumber))
                .willReturn(EsbAccountInfoRes.builder()
                        .accountNumber(accountNumber)
                        .availableBalance(expectedBalance)
                        .build());

        AccountBalance accountBalance = accountService.getBalance(accountNumber);
        assertThat(accountBalance.availableBalance()).isEqualTo(expectedBalance);
    }
}