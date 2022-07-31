package id.co.maybank.jambhala.model;

import lombok.Builder;

@Builder
public record TransferResult (String statusCode, String statusDescription){
}
