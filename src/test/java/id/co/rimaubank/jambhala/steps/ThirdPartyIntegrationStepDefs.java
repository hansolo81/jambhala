package id.co.rimaubank.jambhala.steps;

import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ThirdPartyIntegrationStepDefs {
    private String token;

    @Autowired
    KeyCloakRestUtil keyCloakRestUtil;
    @Given("I am a jambhala user with credentials {string} and {string}")
    public void iAmAJambhalaUserWithCredentialsAnd(String username, String password) {
        token = keyCloakRestUtil.getToken(username, password, "jambhala");
        assertThat(token).isNotBlank();
    }
}
