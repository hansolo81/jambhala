package id.co.maybank.jambhala.service;

import id.co.maybank.jambhala.entity.AccountInfoEsb;
import id.co.maybank.jambhala.model.AccountBalance;
import id.co.maybank.jambhala.model.AccountHolder;
import id.co.maybank.jambhala.model.EsbAccountInfoRes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceShould {

    private AccountService accountService;

    @Mock
    AccountInfoEsb accountInfoEsb;
    String pan;
    String accountNumber;

    @Before
    public void initialise() {
        pan = "1600000000000001";
        accountNumber = "10000000066";
        accountService = new AccountService(accountInfoEsb);
    }

    @Test
    public void returnAccountBalance() {

        BigDecimal expectedBalance = BigDecimal.valueOf(10001.00).setScale(2, RoundingMode.CEILING);
        given(accountInfoEsb.getAccountInfo(pan, accountNumber))
                .willReturn(EsbAccountInfoRes.builder()
                        .accountNumber(accountNumber)
                        .availableBalance(expectedBalance).build());

        AccountBalance actual = accountService.getBalance(pan, accountNumber);
        assertThat(actual.getAvailableBalance()).isEqualTo(expectedBalance);
    }

    @Test
    public void returnAccountHolderName() {

        String expectedHolderName = "padme";
        given(accountInfoEsb.getAccountInfo(pan, accountNumber))
                .willReturn(EsbAccountInfoRes.builder()
                        .accountNumber(accountNumber)
                        .accountHolderName(expectedHolderName)
                        .build());

        AccountHolder actual = accountService.getAccountHolder(pan, accountNumber);
        assertThat(actual.getHolderName()).isEqualTo(expectedHolderName);

    }

}