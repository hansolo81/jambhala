package id.co.maybank.jambhala.config;

import id.co.maybank.jambhala.JambhalaApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest
class CucumberSpringConfiguration {
}
