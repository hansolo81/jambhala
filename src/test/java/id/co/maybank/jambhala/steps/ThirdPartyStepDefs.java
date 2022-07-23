package id.co.maybank.jambhala.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import id.co.maybank.jambhala.model.AccountBalance;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ThirdPartyStepDefs {
    @Autowired
    private KeyCloakRestUtil keyCloak;

    @Autowired
    private MockMvc mockMvc;

    public static WireMockServer wiremock = new WireMockServer(9180);

    private String accessToken;

    @Given("I am a Maybank2u user with credentials {string} and {string}")
    public void userIsAMaybankUUserWithCredentialsAnd(String arg1, String arg2) {
        accessToken = keyCloak.getToken(arg1, arg2, "jambhala");
        assertThat(accessToken).isNotBlank();
    }

    @And("I have a valid account number {string} with balance of {bigdecimal}")
    public void userHasAValidAccountNumberWithSufficientBalance(String accountNumber, BigDecimal amount) {
        try {
            wiremock.start();
            AccountBalance expected = new AccountBalance("1000000066", amount);
            wiremock.stubFor(
                    WireMock.post(urlPathEqualTo("/account-service"))
                            .willReturn(ok()
                                    .withHeader("Content-Type", "application/json")
                                    .withBody(new ObjectMapper().writeValueAsString(expected)))
            );

            MvcResult result = mockMvc.perform(get(String.format("/v1/accounts/%s/balance-inquiry", accountNumber))
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();

            AccountBalance actual = new ObjectMapper().readValue(result.getResponse().getContentAsString()
                    , AccountBalance.class);
            assertThat(actual.getAvailableBalance()).isEqualTo(expected.getAvailableBalance());

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            wiremock.stop();
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
