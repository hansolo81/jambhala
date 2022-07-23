package id.co.maybank.jambhala.service;

import id.co.maybank.jambhala.entity.TransactionEsb;
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

    @Before
    public void initialise() {
        transferService = new TransferService(transactionEsb);
    }

    @Test
    public void doIntrabankSuccessfully() {
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


        TransferResult result = transferService.doIntrabank(pan, transferRequest);
        assertThat(result.getStatusCode()).isEqualTo("0");
        assertThat(result.getStatusDescription()).isEqualTo("Successful");
    }
}