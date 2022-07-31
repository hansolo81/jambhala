package id.co.maybank.jambhala.model;

import id.co.maybank.jambhala.entity.Transaction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class TransactionHistory {

    private List<Transaction> transactions = new ArrayList<>();

    public TransactionHistory(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void add(Transaction transaction) {
        transactions.add(transaction);
    }
    public void addAll(List<Transaction> transactions) {
        transactions.addAll(transactions);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

}
