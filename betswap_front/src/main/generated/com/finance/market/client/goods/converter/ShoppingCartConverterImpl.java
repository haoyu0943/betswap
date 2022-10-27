package com.betswap.market.client.goods.converter;

import com.betswap.market.client.goods.dto.ShoppingCartDTO;
import com.betswap.market.infrastruture.goods.entity.ShoppingCartEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class ShoppingCartConverterImpl implements ShoppingCartConverter {

    @Override
    public ShoppingCartDTO entity2dto(ShoppingCartEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();

        shoppingCartDTO.setId( entity.getId() );
        shoppingCartDTO.setUserId( entity.getUserId() );
        shoppingCartDTO.setGoodId( entity.getGoodId() );
        shoppingCartDTO.setShoppingTime( entity.getShoppingTime() );
        shoppingCartDTO.setGoodNumber( entity.getGoodNumber() );

        return shoppingCartDTO;
    }
}
