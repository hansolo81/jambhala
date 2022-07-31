package id.co.maybank.jambhala.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import id.co.maybank.jambhala.entity.PushNotification;
import id.co.maybank.jambhala.entity.Transaction;
import id.co.maybank.jambhala.model.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ThirdPartyStepDefs {
    @Autowired
    private KeyCloakRestUtil keyCloak;

    @Autowired
    private MockMvc mockMvc;

    public static WireMockServer esbmock = new WireMockServer(9180);

    private String accessToken;

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Before
    public void init() {
        esbmock.start();
    }

    @After
    public void cleanup() {
        esbmock.stop();
    }


    @DataTableType
    public Transaction transactionTransformer(Map<String, String> row) {

        return new Transaction(
                0L,
                row.get("pan"),
                Date.valueOf(LocalDate.parse(row.get("transactionDate"), formatter)),
                row.get("transactionDetails"),
                row.get("fromAccount"),
                row.get("toAccount"),
                new BigDecimal(row.get("amount")),
                row.get("referenceNumber")
        );

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
            esbmock.stubFor(
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
            assertThat(actual.availableBalance()).isEqualTo(balance);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @And("my wife {string} has a valid account number {string}")
    public void myWifeHasAValidAccountNumber(String accountHolderName, String accountNumber) {

        try {
            //given
            esbmock.stubFor(
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

            assertThat(actual.holderName()).isEqualTo(accountHolderName);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @When("I transfer {bigdecimal} from {string} to {string}")
    public void userTransfersToAccountNumber(BigDecimal amount, String fromAccountNumber, String toAccountNumber) {
        try {
            //given
            esbmock.stubFor(
                    WireMock.post(urlPathEqualTo("/trx-service"))
                            .willReturn(ok()
                                    .withHeader("Content-Type", "application/json")
                                    .withBody(new ObjectMapper().writeValueAsString(
                                            EsbTrxRes.builder()
                                                    .statusCode("0")
                                                    .statusDescription("Success")
                                                    .build()
                                    )))
            );

            //when
            MvcResult result = mockMvc.perform(post("/v1/transfer/intrabank")
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(new ObjectMapper().writeValueAsString(
                                    TransferRequest.builder()
                                            .fromAccountNumber(fromAccountNumber)
                                            .toAccountNumber(toAccountNumber)
                                            .amount(amount)
                                            .build()
                            )))
                    .andExpect(status().isOk())
                    .andReturn();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Then("I should receive a message saying Your fund transfer of {bigdecimal} to {string} is successful")
    public void theCustomerShouldReceiveAMessageSayingYourFundTransferOfToIsSuccessful(BigDecimal amount, String recipient) {
        //when
        try {
            MvcResult result = mockMvc.perform(get("/v1/push-notifications/new")
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();
            PushNotification actual = new ObjectMapper().readValue(result.getResponse().getContentAsString()
                    , PushNotification.class);
            assertThat(actual.getMessage()).isEqualTo(String.format("Your fund transfer of %f to %s is successful", amount, recipient));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @And("my transaction history for account number {string} reads like below")
    public void myTransactionHistoryForAccountNumberReadsLikeBelow(String accountNumber, List<Transaction> expected) {
        try {
            MvcResult result = mockMvc.perform(get(String.format("/v1/transactions/%s", accountNumber))
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();

            TransactionHistory txnHistory = new ObjectMapper().readValue(result.getResponse().getContentAsString()
                    , TransactionHistory.class);
            List<Transaction> actualTransactions = txnHistory.getTransactions();

            assertThat(expected.size()).isEqualTo(actualTransactions.size());

            Iterator<Transaction> expectedIt = expected.iterator();
            //then
            while (expectedIt.hasNext()) {
                Transaction ex = expectedIt.next();
                Transaction ac = actualTransactions.iterator().next();
//                assertThat(ex.getTransactionDate()).isEqualTo(ac.getTransactionDate());
                assertThat(ex.getTransactionDetails()).isEqualTo(ac.getTransactionDetails());
                assertThat(ex.getFromAccount()).isEqualTo(ac.getFromAccount());
                assertThat(ex.getToAccount()).isEqualTo(ac.getToAccount());
                assertThat(ex.getAmount()).isEqualTo(ac.getAmount());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
