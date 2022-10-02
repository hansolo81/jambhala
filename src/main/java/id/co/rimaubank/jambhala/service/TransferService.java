package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.entity.MonetaryTransaction;
import id.co.rimaubank.jambhala.event.TransactionSuccessfulEventPublisher;
import id.co.rimaubank.jambhala.mapper.EsbConverter;
import id.co.rimaubank.jambhala.model.EsbTransferRes;
import id.co.rimaubank.jambhala.model.TransferRequest;
import id.co.rimaubank.jambhala.model.TransferResponse;
import id.co.rimaubank.jambhala.service.esb.EsbStatus;
import id.co.rimaubank.jambhala.service.esb.TransferEsb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class TransferService {

    TransferEsb transferEsb;

    TransactionSuccessfulEventPublisher transactionEventPublisher;

    TransferService(TransferEsb transferEsb, TransactionSuccessfulEventPublisher transactionEventPublisher) {
        this.transferEsb = transferEsb;
        this.transactionEventPublisher = transactionEventPublisher;
    }

    public TransferResponse doTransfer(TransferRequest transferRequest, String custNo) {
        EsbTransferRes esbTransferRes = transferEsb.doTransfer(
                EsbConverter.MAPPER.convertToEsbTransferRequest(transferRequest));
        TransferResponse transferResponse = EsbConverter.MAPPER.convertToTransferResponse(
                esbTransferRes
        );
        if (EsbStatus.SUCCESS.equals(transferResponse.getEsbStatus())) {
            transactionEventPublisher.publishEvent(
                    MonetaryTransaction.builder()
                            .custNo(custNo)
                            .amount(transferRequest.amount())
                            .destinationAccount(transferRequest.toAccountNumber())
                            .sourceAccount(transferRequest.fromAccountNumber())
                            .payeeName(transferRequest.payeeName())
                            .transactionType("third party transfer")
                            .transactionDate(new Date())
                            .status("successful")
                            .build());
        }
        return transferResponse;
    }
}
