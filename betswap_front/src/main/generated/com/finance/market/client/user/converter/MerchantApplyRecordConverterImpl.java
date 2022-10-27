package com.betswap.market.client.user.converter;

import com.betswap.market.client.user.dto.MerchantApplyRecordDTO;
import com.betswap.market.client.user.vo.cmd.UserShopCmd;
import com.betswap.market.client.user.vo.cmd.UserShopCmd.UserShopCmdBuilder;
import com.betswap.market.infrastruture.user.entity.MerchantApplyRecordEntity;
import com.betswap.market.infrastruture.user.entity.MerchantApplyRecordEntity.MerchantApplyRecordEntityBuilder;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class MerchantApplyRecordConverterImpl implements MerchantApplyRecordConverter {

    @Override
    public MerchantApplyRecordEntity cmd2entity(UserShopCmd cmd) {
        if ( cmd == null ) {
            return null;
        }

        MerchantApplyRecordEntityBuilder merchantApplyRecordEntity = MerchantApplyRecordEntity.builder();

        merchantApplyRecordEntity.name( cmd.getName() );
        merchantApplyRecordEntity.content( cmd.getContent() );
        merchantApplyRecordEntity.storeCover( cmd.getStoreCover() );
        merchantApplyRecordEntity.storeIntroduction( cmd.getStoreIntroduction() );

        return merchantApplyRecordEntity.build();
    }

    @Override
    public UserShopCmd entity2cmd(MerchantApplyRecordEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserShopCmdBuilder userShopCmd = UserShopCmd.builder();

        userShopCmd.name( entity.getName() );
        userShopCmd.content( entity.getContent() );
        userShopCmd.storeCover( entity.getStoreCover() );
        userShopCmd.storeIntroduction( entity.getStoreIntroduction() );

        return userShopCmd.build();
    }

    @Override
    public MerchantApplyRecordDTO entity2dto(MerchantApplyRecordEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MerchantApplyRecordDTO merchantApplyRecordDTO = new MerchantApplyRecordDTO();

        merchantApplyRecordDTO.setRecordId( entity.getRecordId() );
        merchantApplyRecordDTO.setUserId( entity.getUserId() );
        merchantApplyRecordDTO.setName( entity.getName() );
        merchantApplyRecordDTO.setContent( entity.getContent() );
        merchantApplyRecordDTO.setStoreCover( entity.getStoreCover() );
        merchantApplyRecordDTO.setStoreIntroduction( entity.getStoreIntroduction() );
        merchantApplyRecordDTO.setStatus( entity.getStatus() );
        merchantApplyRecordDTO.setReviewer( entity.getReviewer() );
        merchantApplyRecordDTO.setOpinion( entity.getOpinion() );
        merchantApplyRecordDTO.setOpinionTime( entity.getOpinionTime() );
        merchantApplyRecordDTO.setCreateTime( entity.getCreateTime() );

        return merchantApplyRecordDTO;
    }
}
