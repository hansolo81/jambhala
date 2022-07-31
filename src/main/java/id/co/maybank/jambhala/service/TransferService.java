package id.co.maybank.jambhala.service;

import id.co.maybank.jambhala.entity.PushNotification;
import id.co.maybank.jambhala.entity.TransactionEsb;
import id.co.maybank.jambhala.exception.TransferException;
import id.co.maybank.jambhala.mapper.ESBConverter;
import id.co.maybank.jambhala.model.AccountHolder;
import id.co.maybank.jambhala.model.EsbTrxRes;
import id.co.maybank.jambhala.model.TransferRequest;
import id.co.maybank.jambhala.model.TransferResult;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    TransactionEsb transactionESB;

    PushNotificationService notificationService;

    AccountService accountService;

    public TransferService(TransactionEsb transactionESB, PushNotificationService pushNotificationService, AccountService accountService) {
        this.transactionESB = transactionESB;
        this.notificationService = pushNotificationService;
        this.accountService = accountService;
    }

    public TransferResult doIntrabank(String pan, TransferRequest request) {
        EsbTrxRes esbTrxRes = transactionESB.doTransaction(pan, request);
        if (esbTrxRes.statusCode().equals("0")) {
            AccountHolder accountHolder = accountService.getAccountHolder(pan, request.toAccountNumber());
            notificationService.save(PushNotification.builder()
                    .pan(pan)
                    .message(String.format("Your fund transfer of %f to %s is successful", request.amount(), accountHolder.holderName()))
                    .read(false)
                    .build());
            return ESBConverter.MAPPER.convertToTransferResult(esbTrxRes);
        }
        throw new TransferException();
    }
}
