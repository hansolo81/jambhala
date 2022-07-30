package id.co.maybank.jambhala.controller;

import id.co.maybank.jambhala.model.TransferRequest;
import id.co.maybank.jambhala.model.TransferResult;
import id.co.maybank.jambhala.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transfer")
@Slf4j
public class TransferController {

    TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/intrabank")
    public ResponseEntity<TransferResult> doIntrabankTransfer(@AuthenticationPrincipal Jwt jwt, @RequestBody TransferRequest request) {
        TransferResult transferResult = transferService.doIntrabank(jwt.getClaimAsString("pan"), request);
        return new ResponseEntity<>(transferResult, HttpStatus.OK);
    }


}
