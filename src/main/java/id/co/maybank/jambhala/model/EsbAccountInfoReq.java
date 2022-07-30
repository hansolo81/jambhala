package id.co.maybank.jambhala.model;

import lombok.Builder;
import lombok.ToString;

@Builder
public record EsbAccountInfoReq (String pan , String accountNumber){
}
