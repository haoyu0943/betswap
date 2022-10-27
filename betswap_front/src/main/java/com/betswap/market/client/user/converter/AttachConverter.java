package com.betswap.market.client.user.converter;

import com.betswap.market.client.common.vo.cmd.AttachCmd;
import com.betswap.market.infrastruture.user.entity.QualificationsAttachEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttachConverter {
    AttachConverter INSTANCE = Mappers.getMapper(AttachConverter.class);

    QualificationsAttachEntity cmd2entity(AttachCmd cmd);

    AttachCmd entity2cmd( QualificationsAttachEntity entity);

}
