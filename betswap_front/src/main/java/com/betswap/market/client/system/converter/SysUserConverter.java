package com.betswap.market.client.system.converter;

import com.betswap.market.client.system.dto.SysUserDTO;
import com.betswap.market.infrastruture.system.entity.SysUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserConverter {
    SysUserConverter INSTANCE = Mappers.getMapper(SysUserConverter.class);

    SysUserDTO entity2dto(SysUserEntity cmd);





}
