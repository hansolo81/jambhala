package id.co.maybank.jambhala.model;

import lombok.Builder;

@Builder
public class TransferResult {
    String statusCode;
    String statusDescription;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
