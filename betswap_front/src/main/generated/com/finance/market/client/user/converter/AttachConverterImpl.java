package com.betswap.market.client.user.converter;

import com.betswap.market.client.common.vo.cmd.AttachCmd;
import com.betswap.market.infrastruture.goods.entity.GoodsAttachEntity;
import com.betswap.market.infrastruture.goods.entity.GoodsAttachEntity.GoodsAttachEntityBuilder;
import com.betswap.market.infrastruture.user.entity.QualificationsAttachEntity;
import com.betswap.market.infrastruture.user.entity.QualificationsAttachEntity.QualificationsAttachEntityBuilder;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class AttachConverterImpl implements AttachConverter {

    @Override
    public QualificationsAttachEntity cmd2entity(AttachCmd cmd) {
        if ( cmd == null ) {
            return null;
        }

        QualificationsAttachEntityBuilder qualificationsAttachEntity = QualificationsAttachEntity.builder();

        qualificationsAttachEntity.filename( cmd.getFilename() );
        qualificationsAttachEntity.url( cmd.getUrl() );

        return qualificationsAttachEntity.build();
    }

    @Override
    public AttachCmd entity2cmd(QualificationsAttachEntity entity) {
        if ( entity == null ) {
            return null;
        }

        AttachCmd attachCmd = new AttachCmd();

        attachCmd.setFilename( entity.getFilename() );
        attachCmd.setUrl( entity.getUrl() );

        return attachCmd;
    }

    @Override
    public GoodsAttachEntity cmd2entityg(AttachCmd cmd) {
        if ( cmd == null ) {
            return null;
        }

        GoodsAttachEntityBuilder goodsAttachEntity = GoodsAttachEntity.builder();

        goodsAttachEntity.filename( cmd.getFilename() );
        goodsAttachEntity.url( cmd.getUrl() );

        return goodsAttachEntity.build();
    }

    @Override
    public AttachCmd entityg2cmd(GoodsAttachEntity entity) {
        if ( entity == null ) {
            return null;
        }

        AttachCmd attachCmd = new AttachCmd();

        attachCmd.setFilename( entity.getFilename() );
        attachCmd.setUrl( entity.getUrl() );

        return attachCmd;
    }
}
