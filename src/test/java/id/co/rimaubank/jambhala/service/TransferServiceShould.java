package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.model.TransferRequest;
import id.co.rimaubank.jambhala.model.TransferResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TransferServiceShould {

    TransferService transferService;

    @Before
    public void setUp() throws Exception {
        transferService = new TransferService();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void doTransferSuccessfully() {
        TransferRequest transferRequest = TransferRequest.builder()
                .fromAccountNumber("1000000066")
                .toAccountNumber("1000000099")
                .amount(new BigDecimal("10000.00"))
                .build();
        TransferResponse transferResponse = transferService.doTransfer(transferRequest);
        assertThat(transferResponse.statusCode()).isEqualTo("0");
    }


}