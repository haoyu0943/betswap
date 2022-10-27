package com.betswap.market.client.flow.converter;

import com.betswap.market.client.flow.dto.RevenueRecordDTO;
import com.betswap.market.infrastruture.flow.entity.RevenueRecordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RevenueRecordConverter {
    RevenueRecordConverter INSTANCE = Mappers.getMapper(RevenueRecordConverter.class);

    RevenueRecordDTO entity2dto(RevenueRecordEntity entity);


    RevenueRecordEntity dto2entity(RevenueRecordDTO dto);
}
