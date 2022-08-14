package id.co.maybank.jambhala.steps;

import id.co.maybank.jambhala.config.KeyCloakRestUtil;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ThirdPartyIntegrationStepDefs {

    @Autowired
    private KeyCloakRestUtil keyCloakRestUtil;
    @Given("I am a Maybank2u user with credentials {string} and {string}")
    public void iAmAMaybankUUserWithCredentialsAnd(String username, String password) {
        String token = keyCloakRestUtil.getToken(username, password, "jambhala");
        assertThat(token).isNotBlank();
    }

}
