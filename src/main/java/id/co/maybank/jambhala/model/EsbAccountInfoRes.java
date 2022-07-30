package id.co.maybank.jambhala.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record EsbAccountInfoRes (String accountNumber,
                                BigDecimal availableBalance,
                                BigDecimal holdAmount,
                                BigDecimal floatAmount,
                                String accountHolderName){
}
