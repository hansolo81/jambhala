package id.co.rimaubank.jambhala.controller;

import id.co.rimaubank.jambhala.model.AccountBalance;
import id.co.rimaubank.jambhala.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/v1/accounts")
public class AccountController {

    AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountNumber}/balance-inquiry")
    public ResponseEntity<AccountBalance> getAccountBalance(@PathVariable String accountNumber) {

       return new ResponseEntity<>(
               accountService.getBalance(accountNumber),
               HttpStatus.OK) ;
    }

}
