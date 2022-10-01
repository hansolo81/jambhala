package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.entity.MonetaryTransaction;
import id.co.rimaubank.jambhala.model.TransactionHistory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class TransactionHistoryService {

    public TransactionHistory getTransactionHistory(String custNo, String accountNumber) {
        return new TransactionHistory(
                List.of(MonetaryTransaction.builder()
                        .id(1L)
                        .custNo(custNo)
                        .sourceAccount(accountNumber)
                        .destinationAccount("1000000099")
                        .amount(new BigDecimal("10000.00"))
                        .transactionType("third party transfer")
                        .transactionDate(new Date())
                        .build()));
    }
}
