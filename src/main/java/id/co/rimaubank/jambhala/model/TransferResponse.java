package id.co.rimaubank.jambhala.model;

import id.co.rimaubank.jambhala.service.esb.EsbStatus;
import lombok.Builder;

@Builder
public record TransferResponse(String statusCode, String statusDesc) {
    public EsbStatus getEsbStatus() {
        return EsbStatus.resolve(statusCode) ;
    }
}
