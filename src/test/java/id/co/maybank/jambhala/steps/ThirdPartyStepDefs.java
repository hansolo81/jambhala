package id.co.maybank.jambhala.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import id.co.maybank.jambhala.model.AccountBalance;
import id.co.maybank.jambhala.model.AccountHolder;
import id.co.maybank.jambhala.model.EsbAccountInfoRes;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
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

    @Before
    public void init() {
        wiremock.start();
    }

    @After
    public void cleanup() {
        wiremock.stop();
    }

    @Given("I am a Maybank2u user with credentials {string} and {string}")
    public void userIsAMaybankUUserWithCredentialsAnd(String arg1, String arg2) {
        accessToken = keyCloak.getToken(arg1, arg2, "jambhala");
        assertThat(accessToken).isNotBlank();
    }

    @And("I have a valid account number {string} with balance of {bigdecimal}")
    public void userHasAValidAccountNumberWithSufficientBalance(String accountNumber, BigDecimal balance) {

        try {
            //given
            wiremock.stubFor(
                    WireMock.post(urlPathEqualTo("/account-service"))
                            .willReturn(ok()
                                    .withHeader("Content-Type", "application/json")
                                    .withBody(new ObjectMapper().writeValueAsString(
                                            EsbAccountInfoRes.builder()
                                                    .accountNumber(accountNumber)
                                                    .availableBalance(balance)
                                                    .build()
                                    )))
            );

            //when
            MvcResult result = mockMvc.perform(get(String.format("/v1/accounts/%s/balance-inquiry", accountNumber))
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();

            //then
            AccountBalance actual = new ObjectMapper().readValue(result.getResponse().getContentAsString()
                    , AccountBalance.class);
            assertThat(actual.getAvailableBalance()).isEqualTo(balance);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @And("my wife {string} has a valid account number {string}")
    public void myWifeHasAValidAccountNumber(String accountHolderName, String accountNumber) {

        try {
            //given
            wiremock.stubFor(
                    WireMock.post(urlPathEqualTo("/account-service"))
                            .willReturn(ok()
                                    .withHeader("Content-Type", "application/json")
                                    .withBody(new ObjectMapper().writeValueAsString(
                                            EsbAccountInfoRes.builder()
                                                    .accountNumber(accountNumber)
                                                    .accountHolderName(accountHolderName)
                                                    .build()
                                    )))
            );

            //when
            MvcResult result = mockMvc.perform(get(String.format("/v1/accounts/%s/holder-name", accountNumber))
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();

            //then
            AccountHolder actual = new ObjectMapper().readValue(result.getResponse().getContentAsString(),
                    AccountHolder.class);

            assertThat(actual.getHolderName()).isEqualTo(accountHolderName);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @When("I transfer {bigdecimal} from {string} to {string}")
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
