package id.co.rimaubank.jambhala;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        plugin = {"pretty", "html:target/cucumber-html-report.htm", "json:target/cucumber.json"},
        glue = {"id.co.rimaubank.jambhala"})
public class ThirdPartyIntegrationTest {
}