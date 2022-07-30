package id.co.maybank.jambhala.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record EsbTrxRes (String statusCode, String statusDescription){
}
