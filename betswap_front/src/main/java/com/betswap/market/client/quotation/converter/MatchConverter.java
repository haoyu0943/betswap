package com.betswap.market.client.quotation.converter;

import com.betswap.market.client.quotation.dto.MatchDTO;
import com.betswap.market.client.quotation.dto.QuotationDTO;
import com.betswap.market.infrastruture.quotation.entity.MatchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MatchConverter {
    MatchConverter INSTANCE = Mappers.getMapper(MatchConverter.class);


    MatchDTO entity2dto (MatchEntity entity);
}
