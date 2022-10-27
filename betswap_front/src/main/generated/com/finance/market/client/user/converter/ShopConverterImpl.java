package com.betswap.market.client.user.converter;

import com.betswap.market.client.user.dto.ShopDTO;
import com.betswap.market.client.user.vo.dto.UserShopDto;
import com.betswap.market.client.user.vo.dto.UserShopDto.UserShopDtoBuilder;
import com.betswap.market.infrastruture.user.entity.ShopEntity;
import com.betswap.market.infrastruture.user.entity.ShopEntity.ShopEntityBuilder;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class ShopConverterImpl implements ShopConverter {

    @Override
    public ShopEntity dto2entity(ShopDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ShopEntityBuilder shopEntity = ShopEntity.builder();

        shopEntity.shopId( dto.getShopId() );
        shopEntity.userId( dto.getUserId() );
        shopEntity.name( dto.getName() );
        shopEntity.content( dto.getContent() );
        shopEntity.storeCover( dto.getStoreCover() );
        shopEntity.storeIntroduction( dto.getStoreIntroduction() );
        shopEntity.recordId( dto.getRecordId() );
        shopEntity.createTime( dto.getCreateTime() );

        return shopEntity.build();
    }

    @Override
    public ShopDTO entity2dto(ShopEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ShopDTO shopDTO = new ShopDTO();

        shopDTO.setShopId( entity.getShopId() );
        shopDTO.setUserId( entity.getUserId() );
        shopDTO.setName( entity.getName() );
        shopDTO.setContent( entity.getContent() );
        shopDTO.setStoreCover( entity.getStoreCover() );
        shopDTO.setStoreIntroduction( entity.getStoreIntroduction() );
        shopDTO.setRecordId( entity.getRecordId() );
        shopDTO.setCreateTime( entity.getCreateTime() );

        return shopDTO;
    }

    @Override
    public UserShopDto entity2dto1(ShopEntity shop) {
        if ( shop == null ) {
            return null;
        }

        UserShopDtoBuilder userShopDto = UserShopDto.builder();

        userShopDto.shopId( shop.getShopId() );
        userShopDto.name( shop.getName() );
        userShopDto.content( shop.getContent() );
        userShopDto.storeCover( shop.getStoreCover() );
        userShopDto.storeIntroduction( shop.getStoreIntroduction() );
        userShopDto.starLevel( shop.getStarLevel() );

        return userShopDto.build();
    }
}
