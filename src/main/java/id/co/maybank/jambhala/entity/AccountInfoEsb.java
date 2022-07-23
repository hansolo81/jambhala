package id.co.maybank.jambhala.entity;

import id.co.maybank.jambhala.model.EsbAccountInfoRes;
import id.co.maybank.jambhala.model.EsbAccountInfoReq;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class AccountInfoEsb {

    @Value("${esb.accountservice.url}")
    private String ESB_MAYBANK_CO_ID_ACCOUNT_SERVICE;
    RestTemplate restTemplate;

    public AccountInfoEsb(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public EsbAccountInfoRes getAccountInfo(String pan, String accountNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_XML_VALUE));
        EsbAccountInfoReq req = EsbAccountInfoReq.builder().pan(pan).accountNumber(accountNumber).build();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<EsbAccountInfoRes> responseEntity = restTemplate.postForEntity(
                ESB_MAYBANK_CO_ID_ACCOUNT_SERVICE,
                request,
                EsbAccountInfoRes.class);
        return responseEntity.getBody();
    }
}
