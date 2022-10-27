package com.betswap.market.client.system.converter;

import com.betswap.market.client.system.dto.DictionaryDTO;
import com.betswap.market.infrastruture.system.entity.DictionaryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictionaryConverter {
    DictionaryConverter INSTANCE = Mappers.getMapper(DictionaryConverter.class);

    DictionaryDTO entity2dto(DictionaryEntity cmd);





}
