package id.co.rimaubank.jambhala.model;

import lombok.Builder;

@Builder
public record TransferResponse(String statusCode, String statusDesc) {
}
