package id.co.maybank.jambhala.service;

import id.co.maybank.jambhala.entity.PushNotification;
import id.co.maybank.jambhala.entity.TransactionEsb;
import id.co.maybank.jambhala.exception.TransferException;
import id.co.maybank.jambhala.model.AccountHolder;
import id.co.maybank.jambhala.model.EsbTrxRes;
import id.co.maybank.jambhala.model.TransferRequest;
import id.co.maybank.jambhala.model.TransferResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceShould {

    private TransferService transferService;
    @Mock
    TransactionEsb transactionEsb;

    @Mock
    PushNotificationService pushNotificationService;

    @Mock
    AccountService accountService;

    @Before
    public void initialise() {
        transferService = new TransferService(transactionEsb, pushNotificationService, accountService);
    }

    @Test
    public void returnSuccessIfSuccessful() {
        String pan = "1600000000000001";

        //given
        TransferRequest transferRequest = TransferRequest.builder()
                .fromAccountNumber("1000000066")
                .toAccountNumber("1000000099")
                .amount(BigDecimal.valueOf(1000.00))
                .build();

        given(transactionEsb.doTransaction(pan, transferRequest))
                .willReturn(EsbTrxRes.builder()
                        .statusCode("0")
                        .statusDescription("Successful")
                        .build());

        given(accountService.getAccountHolder(pan,"1000000099"))
                .willReturn(AccountHolder.builder()
                        .accountNumber("10000000099")
                        .holderName("recipient")
                        .build());

        PushNotification mockNotification = PushNotification.builder()
                .pan(pan)
                .message("Test message")
                .build() ;
        pushNotificationService.save(mockNotification);//.willReturn(mockNotification);


        TransferResult result = transferService.doIntrabank(pan, transferRequest);
        assertThat(result.statusCode()).isEqualTo("0");
        assertThat(result.statusDescription()).isEqualTo("Successful");
    }

    @Test
    public void throwTransferExceptionIfFails() {
        String pan = "1600000000000001";

        //given
        TransferRequest transferRequest = TransferRequest.builder()
                .fromAccountNumber("1000000066")
                .toAccountNumber("1000000099")
                .amount(BigDecimal.valueOf(10000.00))
                .build();

        given(transactionEsb.doTransaction(pan, transferRequest))
                .willReturn(EsbTrxRes.builder()
                        .statusCode("100")
                        .statusDescription("Insufficient Funds")
                        .build());
        assertThrows( TransferException.class, () -> transferService.doIntrabank(pan, transferRequest));
    }
}