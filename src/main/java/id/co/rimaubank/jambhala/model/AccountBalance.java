package id.co.rimaubank.jambhala.model;

import java.math.BigDecimal;

public record AccountBalance(String accountNumber, BigDecimal availableBalance) {
}
