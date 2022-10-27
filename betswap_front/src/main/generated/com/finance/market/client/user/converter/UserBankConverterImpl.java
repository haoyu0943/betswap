package com.betswap.market.client.user.converter;

import com.betswap.market.client.user.dto.UserBankDTO;
import com.betswap.market.client.user.vo.cmd.UpdUserBankCmd;
import com.betswap.market.infrastruture.user.entity.UserBankCardEntity;
import com.betswap.market.infrastruture.user.entity.UserBankCardEntity.UserBankCardEntityBuilder;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class UserBankConverterImpl implements UserBankConverter {

    @Override
    public UserBankDTO entity2dto(UserBankCardEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserBankDTO userBankDTO = new UserBankDTO();

        userBankDTO.setCardId( entity.getCardId() );
        userBankDTO.setUserId( entity.getUserId() );
        if ( entity.getDefaultCard() != null ) {
            userBankDTO.setDefaultCard( entity.getDefaultCard() );
        }
        userBankDTO.setBank( entity.getBank() );
        userBankDTO.setBankNetWork( entity.getBankNetWork() );
        userBankDTO.setBankNum( entity.getBankNum() );
        userBankDTO.setHolder( entity.getHolder() );
        userBankDTO.setCreateTime( entity.getCreateTime() );

        return userBankDTO;
    }

    @Override
    public UserBankCardEntity cmd2entity(UpdUserBankCmd dto) {
        if ( dto == null ) {
            return null;
        }

        UserBankCardEntityBuilder userBankCardEntity = UserBankCardEntity.builder();

        userBankCardEntity.cardId( dto.getCardId() );
        userBankCardEntity.defaultCard( dto.getDefaultCard() );
        userBankCardEntity.bank( dto.getBank() );
        userBankCardEntity.bankNetWork( dto.getBankNetWork() );
        userBankCardEntity.bankNum( dto.getBankNum() );
        userBankCardEntity.holder( dto.getHolder() );

        return userBankCardEntity.build();
    }
}
