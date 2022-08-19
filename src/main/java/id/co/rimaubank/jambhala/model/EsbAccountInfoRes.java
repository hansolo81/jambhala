package id.co.rimaubank.jambhala.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record EsbAccountInfoRes(String accountNumber, BigDecimal availableBalance) {
}
