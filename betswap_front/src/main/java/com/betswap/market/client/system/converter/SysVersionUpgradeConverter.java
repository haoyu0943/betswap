package com.betswap.market.client.system.converter;

import com.betswap.market.client.system.dto.SysVersionUpgradeDTO;
import com.betswap.market.infrastruture.system.entity.SysVersionUpgradeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysVersionUpgradeConverter {
    SysVersionUpgradeConverter INSTANCE = Mappers.getMapper(SysVersionUpgradeConverter.class);

    SysVersionUpgradeDTO entity2dto(SysVersionUpgradeEntity cmd);





}
