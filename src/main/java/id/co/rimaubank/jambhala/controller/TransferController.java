package id.co.rimaubank.jambhala.controller;

import id.co.rimaubank.jambhala.model.TransferRequest;
import id.co.rimaubank.jambhala.model.TransferResponse;
import id.co.rimaubank.jambhala.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/transfer")
public class TransferController {
    TransferService transferService ;

    TransferController(TransferService transferService) {
        transferService = transferService;
    }

    public ResponseEntity<TransferResponse> doTransfer(@RequestBody TransferRequest transferRequest) {

       return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
   }
}
