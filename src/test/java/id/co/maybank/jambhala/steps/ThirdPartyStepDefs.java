package id.co.maybank.jambhala.steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ThirdPartyStepDefs {
    @Autowired
    private KeyCloakRestUtil keyCloak;

    @Autowired
    private MockMvc mockMvc;

    private String accessToken;

    @Given("user is a Maybank2u user with credentials {string} and {string}")
    public void userIsAMaybankUUserWithCredentialsAnd(String arg1, String arg2) {
        accessToken = keyCloak.getToken(arg1, arg2, "jambhala");
        assertThat(accessToken).isNotBlank();
    }

    @And("user has a valid account number {string} with balance of {bigdecimal}")
    public void userHasAValidAccountNumberWithSufficientBalance(String accountNumber, BigDecimal amount) {
        try {
            mockMvc.perform(get(String.format("/v1/accounts/%s/balance-inquiry", accountNumber))
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("availableBalance").value(amount));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @When("user transfers {int} to account number {string}")
    public void userTransfersToAccountNumber(int arg0, String arg1) {
        throw new PendingException();
    }

    @Then("the customer should receive a message saying Your fund transfer of {int} to {string} is successful")
    public void theCustomerShouldReceiveAMessageSayingYourFundTransferOfToIsSuccessful(int arg0, String arg1) {
        throw new PendingException();
    }
}
