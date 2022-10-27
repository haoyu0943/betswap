package com.betswap.market.client.user.converter;

import com.betswap.market.client.user.dto.UserWalletDTO;
import com.betswap.market.infrastruture.user.entity.UserWalletEntity;
import com.betswap.market.infrastruture.user.entity.UserWalletEntity.UserWalletEntityBuilder;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class UserWalletConverterImpl implements UserWalletConverter {

    @Override
    public UserWalletEntity dto2entity(UserWalletDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserWalletEntityBuilder userWalletEntity = UserWalletEntity.builder();

        userWalletEntity.userId( dto.getUserId() );
        userWalletEntity.legalCurrency( dto.getLegalCurrency() );
        userWalletEntity.usdt( dto.getUsdt() );
        userWalletEntity.ownBet( dto.getOwnBet() );

        return userWalletEntity.build();
    }

    @Override
    public UserWalletDTO entity2dto(UserWalletEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserWalletDTO userWalletDTO = new UserWalletDTO();

        userWalletDTO.setUserId( entity.getUserId() );
        userWalletDTO.setLegalCurrency( entity.getLegalCurrency() );
        userWalletDTO.setUsdt( entity.getUsdt() );
        userWalletDTO.setOwnBet( entity.getOwnBet() );

        return userWalletDTO;
    }
}
