package id.co.rimaubank.jambhala.controller;

import id.co.rimaubank.jambhala.model.TransactionHistory;
import id.co.rimaubank.jambhala.service.MonetaryTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/transaction-history")
public class TransactionHistoryController {


    MonetaryTransactionService transactionHistoryService;

    public TransactionHistoryController(MonetaryTransactionService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }

    @GetMapping("/{accountNumber}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<TransactionHistory> getTransactionHistory(@AuthenticationPrincipal Jwt jwt, @PathVariable String accountNumber) {
        TransactionHistory transactionHistory = transactionHistoryService.getTransactionHistory(jwt.getClaimAsString("custNo"), accountNumber);

        return new ResponseEntity<>(
                transactionHistory,
                HttpStatus.OK
        );
    }
}
