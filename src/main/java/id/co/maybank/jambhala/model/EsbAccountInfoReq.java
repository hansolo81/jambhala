package id.co.maybank.jambhala.model;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class EsbAccountInfoReq {

    private String pan;
    private String accountNumber;

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

}
