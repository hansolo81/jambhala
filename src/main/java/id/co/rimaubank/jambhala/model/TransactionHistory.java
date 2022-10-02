package id.co.rimaubank.jambhala.model;

import id.co.rimaubank.jambhala.entity.MonetaryTransaction;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class TransactionHistory {

    private List<MonetaryTransaction> transactions = new ArrayList<>();

    public TransactionHistory(List<MonetaryTransaction> transactions) {
        this.transactions = transactions;
    }
}
