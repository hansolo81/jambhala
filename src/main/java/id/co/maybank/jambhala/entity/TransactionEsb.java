package id.co.maybank.jambhala.entity;

import id.co.maybank.jambhala.mapper.ESBConverter;
import id.co.maybank.jambhala.model.*;
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
public class TransactionEsb {

    @Value("${esb.url.trxservice}")
    private String esbTrxServiceUrl;
    RestTemplate restTemplate;

    public TransactionEsb(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public EsbTrxRes doTransaction(String pan, TransferRequest transferRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_XML_VALUE));
        EsbTrxReq req = ESBConverter.MAPPER.convertToEsbTrxReq(transferRequest);
        HttpEntity<String> request = new HttpEntity<>(req.toString(), headers);
        ResponseEntity<EsbTrxRes> responseEntity = restTemplate.postForEntity(
                esbTrxServiceUrl,
                request,
                EsbTrxRes.class);
        return responseEntity.getBody();
    }
}
