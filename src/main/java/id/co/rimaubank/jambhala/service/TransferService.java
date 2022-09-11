package id.co.rimaubank.jambhala.service;

import id.co.rimaubank.jambhala.mapper.EsbConverter;
import id.co.rimaubank.jambhala.model.EsbTransferRes;
import id.co.rimaubank.jambhala.model.TransferRequest;
import id.co.rimaubank.jambhala.model.TransferResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransferService {

    TransferEsb transferEsb;

    TransferService(TransferEsb transferEsb) {
        this.transferEsb = transferEsb;
    }

    public TransferResponse doTransfer(TransferRequest transferRequest) {
        EsbTransferRes esbTransferRes = transferEsb.doTransfer(
                EsbConverter.MAPPER.convertToEsbTransferRequest(transferRequest));
        TransferResponse transferResponse = EsbConverter.MAPPER.convertToTransferResponse(
               esbTransferRes
        );
        return transferResponse;
    }
}
