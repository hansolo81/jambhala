package id.co.maybank.jambhala.service;

import id.co.maybank.jambhala.entity.TransactionEsb;
import id.co.maybank.jambhala.mapper.ESBConverter;
import id.co.maybank.jambhala.model.EsbTrxRes;
import id.co.maybank.jambhala.model.TransferRequest;
import id.co.maybank.jambhala.model.TransferResult;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    TransactionEsb transactionESB;

    public TransferService(TransactionEsb transactionESB) {
        this.transactionESB = transactionESB;
    }

    public TransferResult doIntrabank(String pan, TransferRequest request) {
        EsbTrxRes esbTrxRes = transactionESB.doTransaction(pan, request);
        return ESBConverter.MAPPER.convertToTransferResult(esbTrxRes);
    }
}
