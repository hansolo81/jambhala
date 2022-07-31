package id.co.maybank.jambhala.model;

import id.co.maybank.jambhala.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {

    private List<Transaction> transactions = new ArrayList<>();

    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

}
