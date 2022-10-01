package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.entity.MonetaryTransaction;
import id.co.rimaubank.jambhala.event.TransactionEvent;
import id.co.rimaubank.jambhala.model.TransactionHistory;
import id.co.rimaubank.jambhala.repository.MonetaryTransactionRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonetaryTransactionService {

    public MonetaryTransactionService(MonetaryTransactionRepository monetaryTransactionRepository) {
        this.monetaryTransactionRepository = monetaryTransactionRepository;
    }

    MonetaryTransactionRepository monetaryTransactionRepository;
    public TransactionHistory getTransactionHistory(String custNo, String accountNumber) {
        List<MonetaryTransaction> monetaryTransactions = monetaryTransactionRepository.findByCustNoAndSourceAccount(custNo, accountNumber);
        return new TransactionHistory(monetaryTransactions);
    }


    @EventListener
    public void processTransactionEvent(final TransactionEvent transactionEvent) {
        MonetaryTransaction monetaryTransaction = transactionEvent.getMonetaryTransaction();
        monetaryTransactionRepository.save(monetaryTransaction);
    }
}
