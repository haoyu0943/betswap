package com.betswap.market.client.fund.converter;

import com.betswap.market.client.fund.dto.FundDTO;
import com.betswap.market.client.fund.vo.cmd.CreateFundCmd;
import com.betswap.market.infrastruture.fund.entity.FundEntity;
import com.betswap.market.infrastruture.fund.entity.FundEntity.FundEntityBuilder;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class FundConverterImpl implements FundConverter {

    @Override
    public FundDTO entity2dto(FundEntity entity) {
        if ( entity == null ) {
            return null;
        }

        FundDTO fundDTO = new FundDTO();

        fundDTO.setFundId( entity.getFundId() );
        fundDTO.setFundName( entity.getFundName() );
        fundDTO.setYield( entity.getYield() );
        fundDTO.setCreateTime( entity.getCreateTime() );

        return fundDTO;
    }

    @Override
    public FundEntity cmd2entity(CreateFundCmd cmd) {
        if ( cmd == null ) {
            return null;
        }

        FundEntityBuilder fundEntity = FundEntity.builder();

        fundEntity.fundId( cmd.getFundId() );
        fundEntity.fundName( cmd.getFundName() );
        fundEntity.yield( cmd.getYield() );
        fundEntity.createTime( cmd.getCreateTime() );

        return fundEntity.build();
    }
}
