package com.betswap.market.client.flow.converter;

import com.betswap.market.client.flow.dto.RevenueRecordDTO;
import com.betswap.market.infrastruture.flow.entity.RevenueRecordEntity;
import com.betswap.market.infrastruture.flow.entity.RevenueRecordEntity.RevenueRecordEntityBuilder;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class RevenueRecordConverterImpl implements RevenueRecordConverter {

    @Override
    public RevenueRecordDTO entity2dto(RevenueRecordEntity entity) {
        if ( entity == null ) {
            return null;
        }

        RevenueRecordDTO revenueRecordDTO = new RevenueRecordDTO();

        revenueRecordDTO.setUserId( entity.getUserId() );
        revenueRecordDTO.setOrderNumber( entity.getOrderNumber() );
        revenueRecordDTO.setRevenueType( entity.getRevenueType() );
        revenueRecordDTO.setPayType( entity.getPayType() );
        revenueRecordDTO.setOldAmount( entity.getOldAmount() );
        revenueRecordDTO.setAmount( entity.getAmount() );
        revenueRecordDTO.setNowAmount( entity.getNowAmount() );
        revenueRecordDTO.setCreateTime( entity.getCreateTime() );
        revenueRecordDTO.setRemark( entity.getRemark() );

        return revenueRecordDTO;
    }

    @Override
    public RevenueRecordEntity dto2entity(RevenueRecordDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RevenueRecordEntityBuilder revenueRecordEntity = RevenueRecordEntity.builder();

        revenueRecordEntity.userId( dto.getUserId() );
        revenueRecordEntity.orderNumber( dto.getOrderNumber() );
        revenueRecordEntity.revenueType( dto.getRevenueType() );
        revenueRecordEntity.payType( dto.getPayType() );
        revenueRecordEntity.oldAmount( dto.getOldAmount() );
        revenueRecordEntity.amount( dto.getAmount() );
        revenueRecordEntity.nowAmount( dto.getNowAmount() );
        revenueRecordEntity.createTime( dto.getCreateTime() );
        revenueRecordEntity.remark( dto.getRemark() );

        return revenueRecordEntity.build();
    }
}
