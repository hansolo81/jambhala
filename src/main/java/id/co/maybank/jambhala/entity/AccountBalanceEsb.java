package id.co.maybank.jambhala.entity;

import id.co.maybank.jambhala.model.AccountBalance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class AccountBalanceEsb {

    @Value("${esb.accountservice.url}")
    private String ESB_MAYBANK_CO_ID_ACCOUNT_SERVICE;
    RestTemplate restTemplate;

    public AccountBalanceEsb(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public AccountBalance getBalance(String username, String accountNumber) {
        //TODO: implement Wiremock to stub esb calls
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_XML_VALUE));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<AccountBalance> responseEntity = restTemplate.postForEntity(
                ESB_MAYBANK_CO_ID_ACCOUNT_SERVICE,
                request,
                AccountBalance.class);
        return responseEntity.getBody();
    }
}
