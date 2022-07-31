package id.co.maybank.jambhala.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private Date transactionDate;

    private String transactionDetails;

    private String fromAccount;

    private String toAccount;

    private BigDecimal amount;

    private String referenceNumber;

}
