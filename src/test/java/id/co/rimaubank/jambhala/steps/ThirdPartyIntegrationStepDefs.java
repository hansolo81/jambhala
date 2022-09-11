package id.co.rimaubank.jambhala.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import id.co.rimaubank.jambhala.model.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class ThirdPartyIntegrationStepDefs {
    private String token;

    @Autowired
    KeyCloakRestUtil keyCloakRestUtil;

    @Autowired
    MockMvc mockMvc;

    public static WireMockServer esbMock = new WireMockServer(9010);

    @Before
    public void init() {
        esbMock.start();
    }

    @After
    public void cleanup() {
        esbMock.stop();
    }

    @Given("I am a jambhala user with credentials {string} and {string}")
    public void iAmAJambhalaUserWithCredentialsAnd(String username, String password) {
        token = keyCloakRestUtil.getToken(username, password, "jambhala");
        log.info(token);
        assertThat(token).isNotBlank();
    }

    @And("I have a valid account number {string} with balance of {bigdecimal}")
    public void iHaveAValidAccountNumberWithBalanceOf(String accountNumber, BigDecimal expectedBalance) {
        String url = String.format("/v1/accounts/%s/balance-inquiry", accountNumber);
        try {
            esbMock.stubFor(
                    WireMock.post(WireMock.urlPathEqualTo("/account-service"))
                            .withRequestBody(matchingJsonPath("$.accountNumber", equalTo(accountNumber)))
                            .willReturn(ok()
                                    .withHeader("Content-Type", "application/json")
                                    .withBody(
                                            new ObjectMapper().writeValueAsString(
                                                    EsbAccountInfoRes.builder()
                                                            .accountNumber(accountNumber)
                                                            .availableBalance(expectedBalance)
                                                            .build()
                                            )
                                    )
                            )
            );


            MvcResult expected = mockMvc.perform(get(url)
                            .header("Authorization", token))
                    .andExpect(status().isOk())
                    .andReturn();

            AccountBalance accountBalance = new ObjectMapper().readValue(
                    expected.getResponse().getContentAsString(),
                    AccountBalance.class);

            assertThat(accountBalance.availableBalance()).isEqualTo(expectedBalance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @And("my wife {string} has a valid account number {string}")
    public void myWifeHasAValidAccountNumber(String accountHolderName, String accountNumber) {
        String url = String.format("/v1/accounts/%s/holder-name", accountNumber);

        try {
            //given

            esbMock.stubFor(
                    WireMock.post(WireMock.urlPathEqualTo("/account-service"))
                            .withRequestBody(matchingJsonPath("$.accountNumber", equalTo(accountNumber)))
                            .willReturn(ok()
                                    .withHeader("Content-Type", "application/json")
                                    .withBody(
                                            new ObjectMapper().writeValueAsString(
                                                    EsbAccountInfoRes.builder()
                                                            .accountNumber(accountNumber)
                                                            .holderName(accountHolderName)
                                                            .build()
                                            )
                                    )
                            )
            );

            MvcResult expected = mockMvc.perform(get(url)
                            .header("Authorization", token)
                    )
                    .andExpect(status().isOk())
                    .andReturn();

            AccountHolder accountHolder = new ObjectMapper().readValue(
                    expected.getResponse().getContentAsString(),
                    AccountHolder.class);

            assertThat(accountHolder.holderName()).isEqualTo(accountHolderName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @When("I transfer {bigdecimal} from {string} to {string}")
    public void iTransferFromTo(BigDecimal amount, String fromAccountNumber, String toAccountNumber) {
        String url = "/v1/transfer/intra-bank";

        try {
            esbMock.stubFor(
                    WireMock.post(WireMock.urlPathEqualTo("/transfer-service"))
                            .withRequestBody(matchingJsonPath("$.fromAccountNumber", equalTo(fromAccountNumber)))
                            .willReturn(ok()
                                    .withHeader("Content-Type", "application/json")
                                    .withBody(
                                            new ObjectMapper().writeValueAsString(
                                                    EsbTransferRes.builder()
                                                            .statusCode("0")
                                                            .statusDesc("Success")
                                                            .build()
                                            )
                                    )
                            )
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        TransferRequest transferRequest = TransferRequest.builder()
                .amount(amount)
                .fromAccountNumber(fromAccountNumber)
                .toAccountNumber(toAccountNumber)
                .build();

        try {
            MvcResult result = mockMvc.perform(post(url)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(new ObjectMapper().writeValueAsString(
                                    transferRequest
                            )))
                    .andExpect(status().isOk())
                    .andReturn();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
