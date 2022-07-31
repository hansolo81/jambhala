package id.co.maybank.jambhala.model;

import lombok.Builder;

@Builder
public record AccountHolder (String accountNumber, String holderName) {
}
