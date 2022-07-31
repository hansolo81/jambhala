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

    @Column(nullable = false)
    private String pan;

    @Column(nullable = false)
    private Date transactionDate;

    @Column(nullable = false)
    private String transactionDetails;

    @Column(nullable = false)
    private String fromAccount;

    @Column(nullable = false)
    private String toAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String referenceNumber;

}
