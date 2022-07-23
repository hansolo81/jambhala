package id.co.maybank.jambhala.controller;

import id.co.maybank.jambhala.model.AccountBalance;
import id.co.maybank.jambhala.model.AccountHolder;
import id.co.maybank.jambhala.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {
    Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/{accountNumber}/balance-inquiry")
    public ResponseEntity<AccountBalance> getAccountBalance(@AuthenticationPrincipal Jwt user, @PathVariable String accountNumber)  {
        String pan = user.getClaimAsString("pan");
        return new ResponseEntity<>(
                accountService.getBalance(pan, accountNumber),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{accountNumber}/holder-name")
    public ResponseEntity<AccountHolder> getAccountHolder(@AuthenticationPrincipal Jwt user, @PathVariable String accountNumber) {
        String pan = user.getClaimAsString("pan");
       return new ResponseEntity<>(
               accountService.getAccountHolder(pan, accountNumber),
               HttpStatus.OK
       ) ;
    }
}
