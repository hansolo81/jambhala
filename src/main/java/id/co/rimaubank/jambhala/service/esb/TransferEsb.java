package id.co.rimaubank.jambhala.service.esb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.rimaubank.jambhala.model.EsbTransferRequest;
import id.co.rimaubank.jambhala.model.EsbTransferRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TransferEsb {

    @Value("${esb.url.transfer-service}")
    private String url;

    RestTemplate restTemplate;

    TransferEsb(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }
    public EsbTransferRes doTransfer(EsbTransferRequest esbTransferRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = null;

        try {
            request = new HttpEntity<>(new ObjectMapper().writeValueAsString(esbTransferRequest), headers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<EsbTransferRes> responseEntity = restTemplate.postForEntity(url, request, EsbTransferRes.class);
        return responseEntity.getBody();
    }
}
