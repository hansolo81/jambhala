package id.co.maybank.jambhala.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record AccountBalance(String accountNumber, BigDecimal availableBalance, BigDecimal holdAmount, BigDecimal floatAmount) {
}
