package id.co.maybank.jambhala.service;

import id.co.maybank.jambhala.entity.Transaction;
import id.co.maybank.jambhala.model.TransactionHistory;
import id.co.maybank.jambhala.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TransactionService {
    TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionHistory getTransactionHistory(String pan) {
        List<Transaction> byPan = transactionRepository.findByPan(pan);
        if (byPan == null)
            throw new EntityNotFoundException();

        return new TransactionHistory(byPan);
    }
}
