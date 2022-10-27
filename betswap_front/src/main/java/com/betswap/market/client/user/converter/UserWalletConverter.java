package com.betswap.market.client.user.converter;

import com.betswap.market.client.user.dto.*;
import com.betswap.market.infrastruture.user.entity.UserWalletEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserWalletConverter {
    UserWalletConverter INSTANCE = Mappers.getMapper(UserWalletConverter.class);

    UserWalletEntity dto2entity(UserWalletDTO dto);

//    UserWalletDTO entity2dto(UserWalletEntity entity);

}
