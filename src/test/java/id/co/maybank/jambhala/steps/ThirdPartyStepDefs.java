package id.co.maybank.jambhala.steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ThirdPartyStepDefs {
    @Autowired
    KeyCloakRestUtil keyCloak;

    String token;

    @Given("user is a Maybank2u user with credentials {string} and {string}")
    public void userIsAMaybankUUserWithCredentialsAnd(String arg1, String arg2) {
        token = keyCloak.getToken(arg1, arg2, "jambhala");
        assertThat(token).isNotBlank();
    }

    @And("user has a valid account number {string} with balance of {int}")
    public void userHasAValidAccountNumberWithSufficientBalance(String arg0) {
        throw new PendingException();
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
