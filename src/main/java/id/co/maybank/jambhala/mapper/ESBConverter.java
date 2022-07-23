package id.co.maybank.jambhala.mapper;

import id.co.maybank.jambhala.model.AccountBalance;
import id.co.maybank.jambhala.model.AccountHolder;
import id.co.maybank.jambhala.model.EsbAccountInfoRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ESBConverter {
    ESBConverter MAPPER = Mappers.getMapper(ESBConverter.class);
    AccountBalance convertToAccountBalance(EsbAccountInfoRes esbAccountInfoRes);

    @Mapping(source = "accountHolderName", target = "holderName")
    AccountHolder convertToAccountHolder(EsbAccountInfoRes esbAccountInfoRes);

}
