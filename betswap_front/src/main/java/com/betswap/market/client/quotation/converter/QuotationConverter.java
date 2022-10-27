package com.betswap.market.client.quotation.converter;

import com.betswap.market.client.quotation.cmd.QuotationCmd;
import com.betswap.market.client.quotation.dto.QuotationBitDTO;
import com.betswap.market.infrastruture.quotation.entity.QuotationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuotationConverter {
    QuotationConverter INSTANCE = Mappers.getMapper(QuotationConverter.class);


    QuotationEntity cmd2entity(QuotationCmd cmd);


    QuotationBitDTO entity2dto(QuotationEntity entity);

}
