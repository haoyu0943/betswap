package com.betswap.market.client.communicate.converter;

import com.betswap.market.client.communicate.dto.MovieAdvertisementDTO;
import com.betswap.market.infrastruture.movie.entity.MovieAdvertisementEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class MovieAdvertisementConverterImpl implements MovieAdvertisementConverter {

    @Override
    public MovieAdvertisementDTO entity2dto(MovieAdvertisementEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MovieAdvertisementDTO movieAdvertisementDTO = new MovieAdvertisementDTO();

        movieAdvertisementDTO.setCoverImage( entity.getCoverImage() );
        movieAdvertisementDTO.setLinkUrl( entity.getLinkUrl() );
        movieAdvertisementDTO.setCreateTime( entity.getCreateTime() );
        movieAdvertisementDTO.setUpdateTime( entity.getUpdateTime() );
        movieAdvertisementDTO.setUserName( entity.getUserName() );
        movieAdvertisementDTO.setUserId( entity.getUserId() );
        movieAdvertisementDTO.setUserPhone( entity.getUserPhone() );
        movieAdvertisementDTO.setDelFlg( entity.getDelFlg() );
        movieAdvertisementDTO.setTopFlg( entity.getTopFlg() );
        movieAdvertisementDTO.setTypeFlg( entity.getTypeFlg() );

        return movieAdvertisementDTO;
    }
}
