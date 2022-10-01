package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.entity.MonetaryTransaction;
import id.co.rimaubank.jambhala.model.TransactionHistory;
import id.co.rimaubank.jambhala.repository.MonetaryTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionHistoryService {

    public TransactionHistoryService(MonetaryTransactionRepository monetaryTransactionRepository) {
        this.monetaryTransactionRepository = monetaryTransactionRepository;
    }

    MonetaryTransactionRepository monetaryTransactionRepository;
    public TransactionHistory getTransactionHistory(String custNo, String accountNumber) {
        List<MonetaryTransaction> monetaryTransactions = monetaryTransactionRepository.findByCustNoAndSourceAccount(custNo, accountNumber);
        return new TransactionHistory(monetaryTransactions);
    }
}
