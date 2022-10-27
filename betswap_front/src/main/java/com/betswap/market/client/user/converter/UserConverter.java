package com.betswap.market.client.user.converter;

import com.betswap.market.client.user.dto.*;
import com.betswap.market.infrastruture.user.entity.UserEntity;
import com.betswap.market.client.user.vo.register.RegisterUserCmd;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    UserEntity cmd2entity(RegisterUserCmd cmd);

    UserInfoDTO entity2dto(UserEntity entity);
    OtherUserInfoDTO entity2otherDTO(UserEntity entity);
    CommonUserInfoDTO entity2commonDTO(UserEntity entity);
    UserPageDTO entity2userPageDTO(UserEntity entity);
    LoginUserDTO entity2loginDTO(UserEntity entity);

}
