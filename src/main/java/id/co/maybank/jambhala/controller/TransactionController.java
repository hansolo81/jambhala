package id.co.maybank.jambhala.controller;

import id.co.maybank.jambhala.model.TransactionHistory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    @GetMapping(value = "/{accountNumber}")
    public ResponseEntity<TransactionHistory> getTransactionHistory(@AuthenticationPrincipal Jwt user, @PathVariable String accountNumber) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
