package id.co.rimaubank.jambhala.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import id.co.rimaubank.jambhala.model.AccountBalance;
import id.co.rimaubank.jambhala.model.EsbAccountInfoRes;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
