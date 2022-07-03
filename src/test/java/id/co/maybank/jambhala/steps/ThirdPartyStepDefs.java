package id.co.maybank.jambhala.steps;

import io.cucumber.datatable.DataTable;
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

    @Given("I am a Maybank2u user with credentials {string} and {string}")
    public void userIsAMaybankUUserWithCredentialsAnd(String arg1, String arg2) {
        accessToken = keyCloak.getToken(arg1, arg2, "jambhala");
        assertThat(accessToken).isNotBlank();
    }

    @And("I have a valid account number {string} with balance of {bigdecimal}")
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

    @When("I transfer {bigdecimal} to account number {string} that belongs to {string}")
    public void userTransfersToAccountNumber(BigDecimal arg0, String arg1, String arg2) {
        throw new PendingException();
    }

    @Then("I should receive a message saying Your fund transfer of {bigdecimal} to {string} is successful")
    public void theCustomerShouldReceiveAMessageSayingYourFundTransferOfToIsSuccessful(BigDecimal arg0, String arg1) {
        throw new PendingException();
    }

    @And("my available balance for account number {string} is now {double}")
    public void myAvailableBalanceForAccountNumberIsNow(String arg0, int arg1, int arg2) {
        
    }

    @And("my transaction history for account number {string} reads like below")
    public void myTransactionHistoryForAccountNumberReadsLikeBelow(String arg0, DataTable dataTable) {
    }
}
