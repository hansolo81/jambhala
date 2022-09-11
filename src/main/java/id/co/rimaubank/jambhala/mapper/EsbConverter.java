package id.co.rimaubank.jambhala.mapper;

import id.co.rimaubank.jambhala.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EsbConverter {
        EsbConverter MAPPER = Mappers.getMapper(EsbConverter.class);
        AccountBalance convertToAccountBalance(EsbAccountInfoRes esbAccountInfoRes);
        AccountHolder convertToAccountHolder(EsbAccountInfoRes esbAccountInfoRes);
        TransferResponse convertToTransferResponse(EsbTransferRes esbTransferRes);
        EsbTransferRequest convertToEsbTransferRequest(TransferRequest transferRequest);

}
