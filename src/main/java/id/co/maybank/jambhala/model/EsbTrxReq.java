package id.co.maybank.jambhala.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
public record EsbTrxReq (String pan, String fromAccountNumber, String toAccountNumber, BigDecimal amount){
}
