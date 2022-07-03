package id.co.maybank.jambhala.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KeyCloakRestUtil {
    private static final String CONNECT_TOKEN = "http://127.0.0.1:8080/realms/maybank/protocol/openid-connect/token";

    RestTemplate restTemplate;

    @Autowired
    public KeyCloakRestUtil(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    String getToken(String username, String password, String clientId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("username", username);
        map.add("password", password);
        map.add("grant_type", "password");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> stringResponseEntity = restTemplate
                .postForEntity(CONNECT_TOKEN, request, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(stringResponseEntity.getBody());
            return "Bearer " + jsonNode.get("access_token").toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}