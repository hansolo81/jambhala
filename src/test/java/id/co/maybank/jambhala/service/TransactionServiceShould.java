package id.co.maybank.jambhala.service;

import id.co.maybank.jambhala.entity.Transaction;
import id.co.maybank.jambhala.model.TransactionHistory;
import id.co.maybank.jambhala.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceShould {

    TransactionService transactionService;

    @Mock
    TransactionRepository transactionRepository;
    String pan;

    @Before
    public void init() {
        pan = "1600000000000001";
        transactionService = new TransactionService(transactionRepository);
    }

    @Test
    public void returnTransactionHistory() {
        List<Transaction> expectedTransactions = new ArrayList<>(Arrays.asList(Transaction.builder()
                .id(1L)
                .pan("16000000000001")
                .amount(BigDecimal.valueOf(100))
                .fromAccount("10000000066")
                .toAccount("10000000099")
                .referenceNumber("0000000001")
                .build()));

        given(transactionRepository.findByPan(pan))
                .willReturn(expectedTransactions);

        TransactionHistory transactionHistory = transactionService.getTransactionHistory(pan);
        assertThat(transactionHistory.getTransactions()).isEqualTo(expectedTransactions);
    }

    @Test
    public void throwExceptionIfNoTransactionForPan() {
        given(transactionRepository.findByPan(pan))
                .willReturn(null);

        assertThrows(EntityNotFoundException.class, () -> transactionService.getTransactionHistory(pan));
    }
}