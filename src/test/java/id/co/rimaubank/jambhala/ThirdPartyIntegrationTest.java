package id.co.rimaubank.jambhala;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/thirdparty/",
        plugin = {"pretty", "html:target/cucumber-html-report.htm"},
        glue = {"id.co.rimaubank.jambhala.config", "id.co.rimaubank.jambhala.steps"})
public class ThirdPartyIntegrationTest {
}