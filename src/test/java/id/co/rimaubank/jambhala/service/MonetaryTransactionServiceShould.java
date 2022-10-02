package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.entity.MonetaryTransaction;
import id.co.rimaubank.jambhala.event.TransactionEvent;
import id.co.rimaubank.jambhala.model.TransactionHistory;
import id.co.rimaubank.jambhala.repository.MonetaryTransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class MonetaryTransactionServiceShould {

    MonetaryTransactionService transactionHistoryService;

    @Mock
    MonetaryTransactionRepository monetaryTransactionRepository;

    @Before
    public void setUp() {
        this.transactionHistoryService = new MonetaryTransactionService(monetaryTransactionRepository);
    }

    @Test
    public void returnTransactionHistoryForAccountNumber() {
        String custNo = "0000000001";
        String accountNo = "1000000066";

        TransactionHistory expected = new TransactionHistory(
                List.of(MonetaryTransaction.builder()
                        .id(1L)
                        .sourceAccount(accountNo)
                        .destinationAccount("1000000099")
                        .amount(new BigDecimal("10000.00"))
                        .transactionType("third party transfer")
                        .transactionDate(new Date())
                        .build()));
        List<MonetaryTransaction> expectedTransactions = expected.getTransactions();

        //given
        given(monetaryTransactionRepository.findByCustNoAndSourceAccount(custNo, accountNo)).willReturn(
                expectedTransactions
        );

        //when
        TransactionHistory actual = transactionHistoryService.getTransactionHistory(custNo, accountNo);
        List<MonetaryTransaction> actualTransactions = actual.getTransactions();

        //then
        assertThat(expectedTransactions.size()).isEqualTo(actualTransactions.size());
        for (MonetaryTransaction ex : expectedTransactions) {
            MonetaryTransaction ac = actualTransactions.iterator().next();
//                assertThat(ex.getTransactionDate()).isEqualTo(ac.getTransactionDate());
            assertThat(ex.getTransactionType()).isEqualTo(ac.getTransactionType());
            assertThat(ex.getSourceAccount()).isEqualTo(ac.getSourceAccount());
            assertThat(ex.getDestinationAccount()).isEqualTo(ac.getDestinationAccount());
            assertThat(ex.getAmount()).isEqualTo(ac.getAmount());
        }

    }

    @Test
    public void processTransactionEvent() {
        String custNo = "0000000001";
        String payeeName = "padme";
        BigDecimal amount = new BigDecimal("10000.00");
        String accountNo = "100000000066";

        MonetaryTransaction expected = MonetaryTransaction.builder()
                .custNo(custNo)
                .amount(amount)
                .destinationAccount(accountNo)
                .sourceAccount("100000000099")
                .payeeName(payeeName)
                .transactionType("third party transfer")
                .transactionDate(new Date())
                .status("successful")
                .build();

        //given
        given(monetaryTransactionRepository.save(expected)).willReturn(expected);
        given(monetaryTransactionRepository.findByCustNoAndSourceAccount(custNo, accountNo)).willReturn(List.of(expected));

        TransactionEvent transactionEvent = new TransactionEvent(new Object(), expected);

        transactionHistoryService.processTransactionEvent(transactionEvent);
        TransactionHistory transactionHistory = transactionHistoryService.getTransactionHistory(custNo, accountNo);
        MonetaryTransaction actual = transactionHistory.getTransactions().get(0);
        assertThat(actual).isEqualTo(expected);
    }
}