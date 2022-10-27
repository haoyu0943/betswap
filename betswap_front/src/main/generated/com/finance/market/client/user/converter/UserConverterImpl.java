package com.betswap.market.client.user.converter;

import com.betswap.market.client.user.dto.CommonUserInfoDTO;
import com.betswap.market.client.user.dto.LoginUserDTO;
import com.betswap.market.client.user.dto.LoginUserDTO.LoginUserDTOBuilder;
import com.betswap.market.client.user.dto.OtherUserInfoDTO;
import com.betswap.market.client.user.dto.UserInfoDTO;
import com.betswap.market.client.user.dto.UserPageDTO;
import com.betswap.market.client.user.vo.register.RegisterUserCmd;
import com.betswap.market.infrastruture.user.entity.UserEntity;
import com.betswap.market.infrastruture.user.entity.UserEntity.UserEntityBuilder;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class UserConverterImpl implements UserConverter {

    @Override
    public UserEntity cmd2entity(RegisterUserCmd cmd) {
        if ( cmd == null ) {
            return null;
        }

        UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.userName( cmd.getUserName() );
        userEntity.password( cmd.getPassword() );
        userEntity.userPhone( cmd.getUserPhone() );
        userEntity.userAvatar( cmd.getUserAvatar() );
        userEntity.userIntroduction( cmd.getUserIntroduction() );

        return userEntity.build();
    }

    @Override
    public UserInfoDTO entity2dto(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserInfoDTO userInfoDTO = new UserInfoDTO();

        userInfoDTO.setUserId( entity.getUserId() );
        userInfoDTO.setUserName( entity.getUserName() );
        userInfoDTO.setUserPhone( entity.getUserPhone() );
        userInfoDTO.setUserAvatar( entity.getUserAvatar() );
        userInfoDTO.setUserIntroduction( entity.getUserIntroduction() );
        if ( entity.getOnlineDays() != null ) {
            userInfoDTO.setOnlineDays( String.valueOf( entity.getOnlineDays() ) );
        }
        userInfoDTO.setLatestOnlineTime( entity.getLatestOnlineTime() );
        userInfoDTO.setPrivilege( entity.getPrivilege() );
        userInfoDTO.setTotalBETRevenue( entity.getTotalBETRevenue() );
        userInfoDTO.setTotalUSDTRevenue( entity.getTotalUSDTRevenue() );
        userInfoDTO.setShopInvitationCode( entity.getShopInvitationCode() );
        userInfoDTO.setMoviesInvitationCode( entity.getMoviesInvitationCode() );
        userInfoDTO.setWalletAddress( entity.getWalletAddress() );

        return userInfoDTO;
    }

    @Override
    public OtherUserInfoDTO entity2otherDTO(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        OtherUserInfoDTO otherUserInfoDTO = new OtherUserInfoDTO();

        otherUserInfoDTO.setUserId( entity.getUserId() );
        otherUserInfoDTO.setUserName( entity.getUserName() );
        otherUserInfoDTO.setUserEmail( entity.getUserEmail() );
        otherUserInfoDTO.setUserPhone( entity.getUserPhone() );
        otherUserInfoDTO.setUserAvatar( entity.getUserAvatar() );
        otherUserInfoDTO.setUserIntroduction( entity.getUserIntroduction() );

        return otherUserInfoDTO;
    }

    @Override
    public CommonUserInfoDTO entity2commonDTO(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CommonUserInfoDTO commonUserInfoDTO = new CommonUserInfoDTO();

        commonUserInfoDTO.setUserId( entity.getUserId() );
        commonUserInfoDTO.setUserName( entity.getUserName() );
        commonUserInfoDTO.setUserAvatar( entity.getUserAvatar() );
        commonUserInfoDTO.setUserIntroduction( entity.getUserIntroduction() );
        if ( entity.getBuyTotalAmount() != null ) {
            commonUserInfoDTO.setBuyTotalAmount( entity.getBuyTotalAmount().toString() );
        }
        if ( entity.getSellTotalAmount() != null ) {
            commonUserInfoDTO.setSellTotalAmount( entity.getSellTotalAmount().toString() );
        }

        return commonUserInfoDTO;
    }

    @Override
    public UserPageDTO entity2userPageDTO(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserPageDTO userPageDTO = new UserPageDTO();

        userPageDTO.setUserId( entity.getUserId() );
        userPageDTO.setUserName( entity.getUserName() );
        userPageDTO.setUserEmail( entity.getUserEmail() );
        userPageDTO.setUserPhone( entity.getUserPhone() );
        userPageDTO.setPrivilege( entity.getPrivilege() );

        return userPageDTO;
    }

    @Override
    public LoginUserDTO entity2loginDTO(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        LoginUserDTOBuilder loginUserDTO = LoginUserDTO.builder();

        loginUserDTO.userId( entity.getUserId() );
        loginUserDTO.userName( entity.getUserName() );
        loginUserDTO.userPhone( entity.getUserPhone() );
        loginUserDTO.userEmail( entity.getUserEmail() );
        if ( entity.getPrivilege() != null ) {
            loginUserDTO.privilege( String.valueOf( entity.getPrivilege() ) );
        }
        loginUserDTO.userAvatar( entity.getUserAvatar() );

        return loginUserDTO.build();
    }
}
