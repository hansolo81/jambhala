package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.model.TransferRequest;
import id.co.rimaubank.jambhala.model.TransferResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransferService {

    public TransferResponse doTransfer(TransferRequest transferRequest) {
        throw new NotImplementedException();
    }
}
