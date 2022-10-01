package id.co.rimaubank.jambhala.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransferRequest(BigDecimal amount, String fromAccountNumber, String toAccountNumber,
                              String payeeName) {
}
