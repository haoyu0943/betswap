package com.betswap.market.client.team.converter;

import com.betswap.market.client.team.dto.TeamDTO;
import com.betswap.market.infrastruture.team.entity.TeamMemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeamConverter {
    TeamConverter INSTANCE = Mappers.getMapper(TeamConverter.class);


    TeamDTO entity2dto(TeamMemberEntity entity);



}
