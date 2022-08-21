package id.co.rimaubank.jambhala.controller;

import id.co.rimaubank.jambhala.model.AccountBalance;
import id.co.rimaubank.jambhala.model.AccountHolder;
import id.co.rimaubank.jambhala.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/accounts")
@Slf4j
public class AccountController {

    AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountNumber}/balance-inquiry")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<AccountBalance> getAccountBalance(@AuthenticationPrincipal Jwt jwt, @PathVariable String accountNumber) {
       return new ResponseEntity<>(
               accountService.getBalance(jwt.getClaimAsString("custNo"), accountNumber),
               HttpStatus.OK) ;
    }

    @GetMapping("/{accountNumber}/holder-name")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<AccountHolder> getHolderName(@AuthenticationPrincipal Jwt jwt, @PathVariable String accountNumber) {
        return new ResponseEntity<>(
                accountService.getAccountHolder(jwt.getClaimAsString("custNo"), accountNumber),
                HttpStatus.OK
        );
    }


}
