package id.co.rimaubank.jambhala.repository;

import id.co.rimaubank.jambhala.entity.MonetaryTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonetaryTransactionRepository extends CrudRepository<MonetaryTransaction, Long> {
    List<MonetaryTransaction> findByCustNoAndSourceAccount(String custNo, String sourceAccount);
}
