package com.betswap.market.client.order.converter.fixprice;

import com.betswap.market.client.order.dto.buy_fix_price.BuyFixPriceOrderCreateDTO;
import com.betswap.market.client.order.dto.buy_fix_price.BuyFixPriceOrderDTO;
import com.betswap.market.infrastruture.order.entity.OrderEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class BuyFixPriceOrderConverterImpl implements BuyFixPriceOrderConverter {

    @Override
    public BuyFixPriceOrderDTO entity2dto(OrderEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BuyFixPriceOrderDTO buyFixPriceOrderDTO = new BuyFixPriceOrderDTO();

        buyFixPriceOrderDTO.setOrderId( entity.getOrderId() );
        buyFixPriceOrderDTO.setGoodsId( entity.getGoodsId() );
        buyFixPriceOrderDTO.setStatus( entity.getStatus() );
        buyFixPriceOrderDTO.setCreateTime( entity.getCreateTime() );
        buyFixPriceOrderDTO.setSellerId( entity.getSellerId() );

        return buyFixPriceOrderDTO;
    }

    @Override
    public BuyFixPriceOrderCreateDTO entity2createDTO(OrderEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BuyFixPriceOrderCreateDTO buyFixPriceOrderCreateDTO = new BuyFixPriceOrderCreateDTO();

        buyFixPriceOrderCreateDTO.setOrderId( entity.getOrderId() );
        buyFixPriceOrderCreateDTO.setGoodsId( entity.getGoodsId() );
        buyFixPriceOrderCreateDTO.setStatus( entity.getStatus() );
        buyFixPriceOrderCreateDTO.setCreateTime( entity.getCreateTime() );
        buyFixPriceOrderCreateDTO.setSellerId( entity.getSellerId() );

        return buyFixPriceOrderCreateDTO;
    }
}
