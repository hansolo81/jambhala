package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.mapper.EsbConverter;
import id.co.rimaubank.jambhala.model.EsbTransferRes;
import id.co.rimaubank.jambhala.model.TransferRequest;
import id.co.rimaubank.jambhala.model.TransferResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceShould {

    TransferService transferService;

    @Mock
    TransferEsb transferEsb;

    @Before
    public void initialize() throws Exception {
        transferService = new TransferService(transferEsb);
    }


    @Test
    public void doTransferSuccessfully() {
       TransferRequest transferRequest = TransferRequest.builder()
                .fromAccountNumber("1000000066")
                .toAccountNumber("1000000099")
                .amount(new BigDecimal("10000.00"))
                .build();

       given(transferEsb.doTransfer(EsbConverter.MAPPER.convertToEsbTransferRequest(transferRequest)))
                .willReturn(
                        EsbTransferRes.builder()
                                .statusCode("0")
                                .statusDesc("Success")
                                .build()
                );

        TransferResponse transferResponse = transferService.doTransfer(transferRequest);
        assertThat(transferResponse.statusCode()).isEqualTo("0");
    }


}