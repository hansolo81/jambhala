package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.model.EsbAccountInfoReq;
import id.co.rimaubank.jambhala.model.EsbAccountInfoRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AccountInfoESB {

    @Value("${esb.url.accountservice}")
    private String esbUrl;
    RestTemplate restTemplate;

    public AccountInfoESB(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public EsbAccountInfoRes getAccountInfo(String customerNumber, String accountNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        EsbAccountInfoReq esbAccountInfoReq = new EsbAccountInfoReq(customerNumber, accountNumber);
        HttpEntity<String> request = new HttpEntity<>(esbAccountInfoReq.toString(), headers);
        ResponseEntity<EsbAccountInfoRes> responseEntity = restTemplate.postForEntity(
                esbUrl, request, EsbAccountInfoRes.class);
        return responseEntity.getBody();
    }
}
