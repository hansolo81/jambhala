package id.co.rimaubank.jambhala.controller;

import id.co.rimaubank.jambhala.model.TransferRequest;
import id.co.rimaubank.jambhala.model.TransferResponse;
import id.co.rimaubank.jambhala.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/transfer")
public class TransferController {
    TransferService transferService ;

    TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping(value = "/intra-bank")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<TransferResponse> doTransfer(@AuthenticationPrincipal Jwt jwt, @RequestBody TransferRequest transferRequest) {
        TransferResponse transferResponse = transferService.doTransfer(transferRequest, jwt.getClaimAsString("custNo"));
        return new ResponseEntity<>(transferResponse, HttpStatus.OK);
   }
}
