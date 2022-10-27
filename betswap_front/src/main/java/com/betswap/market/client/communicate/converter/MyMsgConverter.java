package com.betswap.market.client.communicate.converter;

import com.betswap.market.client.communicate.dto.MyMsgDTO;
import com.betswap.market.client.communicate.dto.SysMsgDTO;
import com.betswap.market.infrastruture.communicate.entity.MyMsgEntity;
import com.betswap.market.infrastruture.communicate.entity.SysMsgEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MyMsgConverter {
    MyMsgConverter INSTANCE = Mappers.getMapper(MyMsgConverter.class);

    MyMsgDTO entity2dto(MyMsgEntity entity);

    SysMsgDTO entityS2dto(SysMsgEntity entity);

}
