package com.betswap.market.client.flow.converter;

import com.betswap.market.client.flow.dto.FlowCommonDTO;
import com.betswap.market.client.flow.dto.RevenueRecordDTO;
import com.betswap.market.infrastruture.flow.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlowConverter {
    FlowConverter INSTANCE = Mappers.getMapper(FlowConverter.class);

    FlowCommonDTO entity2dto(USDTRechargeRecordEntity entity);

    FlowCommonDTO entity2dto(BETRechargeRecordEntity entity);

    FlowCommonDTO entity2dto(BETWithdrawalRecordEntity entity);

    FlowCommonDTO entity2dto(USDTWithdrawalRecordEntity entity);
}
