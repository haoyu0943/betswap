package com.betswap.market.client.goods.converter;

import com.betswap.market.client.goods.dto.GoodsAssetDTO;
import com.betswap.market.client.goods.dto.GoodsDTO;
import com.betswap.market.client.goods.vo.cmd.CreateGoodsCmd;
import com.betswap.market.infrastruture.goods.entity.GoodsEntity;
import com.betswap.market.infrastruture.goods.entity.GoodsEntity.GoodsEntityBuilder;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class GoodsConverterImpl implements GoodsConverter {

    @Override
    public GoodsDTO entity2dto(GoodsEntity entity) {
        if ( entity == null ) {
            return null;
        }

        GoodsDTO goodsDTO = new GoodsDTO();

        goodsDTO.setGoodsId( entity.getGoodsId() );
        goodsDTO.setGoodsName( entity.getGoodsName() );
        goodsDTO.setShopId( entity.getShopId() );
        goodsDTO.setCover( entity.getCover() );
        goodsDTO.setIntroduce( entity.getIntroduce() );
        goodsDTO.setBasePrice( entity.getBasePrice() );
        goodsDTO.setSeries( entity.getSeries() );
        goodsDTO.setPublishTime( entity.getPublishTime() );
        goodsDTO.setStatus( entity.getStatus() );
        goodsDTO.setDiscountPrice( entity.getDiscountPrice() );
        goodsDTO.setStartTime( entity.getStartTime() );
        goodsDTO.setEndTime( entity.getEndTime() );
        goodsDTO.setIfGroup( entity.getIfGroup() );
        goodsDTO.setGroupPrice( entity.getGroupPrice() );

        return goodsDTO;
    }

    @Override
    public GoodsAssetDTO entity2assetdto(GoodsEntity entity) {
        if ( entity == null ) {
            return null;
        }

        GoodsAssetDTO goodsAssetDTO = new GoodsAssetDTO();

        goodsAssetDTO.setGoodsId( entity.getGoodsId() );
        goodsAssetDTO.setGoodsName( entity.getGoodsName() );
        goodsAssetDTO.setBasePrice( entity.getBasePrice() );

        return goodsAssetDTO;
    }

    @Override
    public GoodsEntity cmd2entity(CreateGoodsCmd cmd) {
        if ( cmd == null ) {
            return null;
        }

        GoodsEntityBuilder goodsEntity = GoodsEntity.builder();

        goodsEntity.goodsId( cmd.getGoodsId() );
        goodsEntity.goodsName( cmd.getGoodsName() );
        goodsEntity.shopId( cmd.getShopId() );
        goodsEntity.cover( cmd.getCover() );
        goodsEntity.introduce( cmd.getIntroduce() );
        goodsEntity.basePrice( cmd.getBasePrice() );
        goodsEntity.series( cmd.getSeries() );
        goodsEntity.ifGroup( cmd.getIfGroup() );
        goodsEntity.groupPrice( cmd.getGroupPrice() );

        return goodsEntity.build();
    }
}
