package id.co.maybank.jambhala.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransferRequest(
        @JsonProperty String fromAccountNumber,
        @JsonProperty String toAccountNumber,
        @JsonProperty BigDecimal amount) {
}
