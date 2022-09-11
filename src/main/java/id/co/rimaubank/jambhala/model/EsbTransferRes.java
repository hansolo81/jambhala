package id.co.rimaubank.jambhala.model;

import lombok.Builder;

@Builder
public record EsbTransferRes(String statusCode, String statusDesc) {
}
