package id.co.rimaubank.jambhala.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import id.co.rimaubank.jambhala.entity.MonetaryTransaction;
import id.co.rimaubank.jambhala.entity.PushNotification;
import id.co.rimaubank.jambhala.model.*;
import id.co.rimaubank.jambhala.service.esb.EsbStatus;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static WireMockServer esbMock = new WireMockServer(9010);

    @Before
    public void init() {
        esbMock.start();
    }

    @After
    public void cleanup() {
        esbMock.stop();
    }


    @DataTableType
    public MonetaryTransaction transactionTransformer(Map<String, String> row) {

        return MonetaryTransaction.builder()
                .id(Long.parseLong(row.get("referenceNumber")))
                .transactionType(row.get("transactionType"))
                .transactionDate(Date.valueOf(LocalDate.parse(row.get("date"), formatter)))
                .sourceAccount(row.get("fromAccount"))
                .destinationAccount(row.get("destinationAccount"))
                .amount(new BigDecimal(row.get("amount")))
                .build();
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
                                                            .statusCode(EsbStatus.SUCCESS.value())
                                                            .statusDesc(EsbStatus.SUCCESS.getReasonPhrase())
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
                .payeeName("padme")
                .build();

        try {
            mockMvc.perform(post(url)
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

    @Then("I should receive a message saying {string}")
    public void iShouldReceiveAMessageSayingYourFundTransferOfToIsSuccessful(String message) {

        String url = "/v1/push-notifications/new";
        try {
            MvcResult result = mockMvc.perform(get(url)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                    )
                    .andExpect(status().isOk())
                    .andReturn();

            ObjectMapper mapper = new ObjectMapper();

            List<PushNotification> pushNotificationList = mapper.readValue(
                    result.getResponse().getContentAsString(), List.class);

            pushNotificationList = mapper.convertValue(pushNotificationList, new TypeReference<List<PushNotification>>() {
            });

            Boolean messageFound = false;
            for (PushNotification notif : pushNotificationList) {
                if (message.equals(notif.getMessage()) && !notif.getRead()) {
                    messageFound = true;
                    break;
                }
            }
            assertThat(messageFound).isTrue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @And("my transaction history for account number {string} reads like below")
    public void myTransactionHistoryForAccountNumberReadsLikeBelow(String accountNumber, List<MonetaryTransaction> expected) {
        try {
            MvcResult result = mockMvc.perform(get(String.format("/v1/transaction-history/%s", accountNumber))
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();

            TransactionHistory txnHistory = new ObjectMapper().readValue(result.getResponse().getContentAsString()
                    , TransactionHistory.class);
            List<MonetaryTransaction> actualTransactions = txnHistory.getTransactions();

            assertThat(expected.size()).isEqualTo(actualTransactions.size());

            //then
            for (MonetaryTransaction ex : expected) {
                MonetaryTransaction ac = actualTransactions.iterator().next();
//                assertThat(ex.getTransactionDate()).isEqualTo(ac.getTransactionDate());
                assertThat(ex.getTransactionType()).isEqualTo(ac.getTransactionType());
                assertThat(ex.getSourceAccount()).isEqualTo(ac.getSourceAccount());
                assertThat(ex.getDestinationAccount()).isEqualTo(ac.getDestinationAccount());
                assertThat(ex.getAmount()).isEqualTo(ac.getAmount());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
