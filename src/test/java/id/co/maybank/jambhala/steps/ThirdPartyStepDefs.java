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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ThirdPartyStepDefs {

    @Autowired
    private MockMvc mockMvc;

    @Given("user is a Maybank2u user with credentials {string} and {string}")
    public void userIsAMaybank2UUserWithCredentialsAnd(String arg1, String arg2) throws Exception {
        mockMvc
                .perform(post("/v1/auth/login")
                        .content(String.format("{\"userName\":\"%s\",\"password\":\"%s\"}", arg1, arg2))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @And("user has a valid account number {string} with balance of {bigdecimal}")
    public void userHasAValidAccountNumberWithSufficientBalance(String arg0, BigDecimal arg1) throws Exception {
        mockMvc
                .perform(get(String.format("/v1/accounts/%s", "anakin")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accounts[0].accountNumber").value(arg0))
                .andExpect(jsonPath("$.accounts[0].accountBalance").value(arg1));
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
