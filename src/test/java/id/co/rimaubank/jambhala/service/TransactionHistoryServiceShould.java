package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.entity.MonetaryTransaction;
import id.co.rimaubank.jambhala.model.TransactionHistory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TransactionHistoryServiceShould {

    TransactionHistoryService transactionHistoryService;

    @Before
    public void setUp() {
        this.transactionHistoryService = new TransactionHistoryService();
    }

    @Test
    public void returnTransactionHistoryForAccountNumber() {
        String custNo = "0000000001";
        String accountNo = "1000000066";
        TransactionHistory expected = new TransactionHistory(
                List.of(MonetaryTransaction.builder()
                        .id(1L)
                        .sourceAccount("1000000066")
                        .destinationAccount("1000000099")
                        .amount(new BigDecimal("10000.00"))
                        .transactionType("third party transfer")
                        .transactionDate(new Date())
                        .build()));

        TransactionHistory actual = transactionHistoryService.getTransactionHistory(custNo, accountNo);

        List<MonetaryTransaction> expectedTransactions = expected.getTransactions();
        List<MonetaryTransaction> actualTransactions = actual.getTransactions();

        assertThat(expectedTransactions.size()).isEqualTo(actualTransactions.size());

        //then
        for (MonetaryTransaction ex : expectedTransactions) {
            MonetaryTransaction ac = actualTransactions.iterator().next();
//                assertThat(ex.getTransactionDate()).isEqualTo(ac.getTransactionDate());
            assertThat(ex.getTransactionType()).isEqualTo(ac.getTransactionType());
            assertThat(ex.getSourceAccount()).isEqualTo(ac.getSourceAccount());
            assertThat(ex.getDestinationAccount()).isEqualTo(ac.getDestinationAccount());
            assertThat(ex.getAmount()).isEqualTo(ac.getAmount());
        }

    }
}