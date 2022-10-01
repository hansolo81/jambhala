package id.co.rimaubank.jambhala.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MonetaryTransaction {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "custNo", nullable = false)
    private String custNo;

    @Column(name = "sourceAccount", nullable = false)
    private String sourceAccount;

    @Column(name = "destinationAccount", nullable = false)
    private String destinationAccount;

    @Column(name = "payeeName", nullable = true)
    private String payeeName;

    @Column(name = "transactionType", nullable = false)
    private String transactionType;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "transactionDate", nullable = false)
    private Date transactionDate;

    @Column(name = "status", nullable = false)
    private String status;

}
