package id.co.rimaubank.jambhala.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.rimaubank.jambhala.model.AccountBalance;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ThirdPartyIntegrationStepDefs {
    private String token;

    @Autowired
    KeyCloakRestUtil keyCloakRestUtil;

    @Autowired
    MockMvc mockMvc;
    @Given("I am a jambhala user with credentials {string} and {string}")
    public void iAmAJambhalaUserWithCredentialsAnd(String username, String password) {
        token = keyCloakRestUtil.getToken(username, password, "jambhala");
        assertThat(token).isNotBlank();
    }

    @And("I have a valid account number {string} with balance of {bigdecimal}")
    public void iHaveAValidAccountNumberWithBalanceOf(String accountNumber, BigDecimal expectedBalance) {
        String url = String.format("/v1/accounts/%s/balance-inquiry", accountNumber);
        try {
            MvcResult mvcResult = mockMvc.perform(get(url))
                    .andExpect(status().isOk())
                    .andReturn();

            AccountBalance accountBalance = new ObjectMapper().readValue(
                    mvcResult.getResponse().getContentAsString(),
                    AccountBalance.class);

            assertThat(accountBalance.availableBalance()).isEqualTo(expectedBalance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
