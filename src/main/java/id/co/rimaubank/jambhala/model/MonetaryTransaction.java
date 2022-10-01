package id.co.rimaubank.jambhala.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
public class MonetaryTransaction {
   private String custNo;
   private String sourceAccount;
   private String destinationAccount;
   private String payeeName;
   private BigDecimal amount;
   private Date transactionDate;
}
