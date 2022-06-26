package id.co.maybank.jambhala;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/thirdparty/",
        plugin = {"pretty", "html:target/cucumber-html-report.html"},
        glue = {"id.co.maybank.jambhala.config", "id.co.maybank.jambhala.steps"})
public class ThirdPartyIntegrationTest {
}