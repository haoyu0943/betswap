package com.betswap.market.client.quotation.converter;

import com.betswap.market.client.quotation.dto.BetRecordDTO;
import com.betswap.market.infrastruture.quotation.entity.BetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BetRecordConverter {
    BetRecordConverter INSTANCE = Mappers.getMapper(BetRecordConverter.class);


    BetRecordDTO entity2dto (BetEntity entity);
}
