package com.betswap.market.client.user.converter;

import com.betswap.market.client.user.dto.UserAddressDTO;
import com.betswap.market.client.user.dto.UserAddressDTO.UserAddressDTOBuilder;
import com.betswap.market.client.user.vo.cmd.UserAddressCmd;
import com.betswap.market.infrastruture.user.entity.UserAddressEntity;
import com.betswap.market.infrastruture.user.entity.UserAddressEntity.UserAddressEntityBuilder;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class UserAddressConverterImpl implements UserAddressConverter {

    @Override
    public UserAddressDTO entity2dto(UserAddressEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserAddressDTOBuilder userAddressDTO = UserAddressDTO.builder();

        userAddressDTO.userId( entity.getUserId() );
        userAddressDTO.addressId( entity.getAddressId() );
        userAddressDTO.userAddress( entity.getUserAddress() );
        userAddressDTO.phone( entity.getPhone() );
        userAddressDTO.name( entity.getName() );
        userAddressDTO.addressType( entity.getAddressType() );
        userAddressDTO.defaultKey( entity.getDefaultKey() );

        return userAddressDTO.build();
    }

    @Override
    public UserAddressEntity cmd2entity(UserAddressCmd cmd) {
        if ( cmd == null ) {
            return null;
        }

        UserAddressEntityBuilder userAddressEntity = UserAddressEntity.builder();

        userAddressEntity.addressId( cmd.getAddressId() );
        userAddressEntity.userAddress( cmd.getUserAddress() );
        userAddressEntity.phone( cmd.getPhone() );
        userAddressEntity.name( cmd.getName() );
        userAddressEntity.addressType( cmd.getAddressType() );
        userAddressEntity.defaultKey( cmd.getDefaultKey() );

        return userAddressEntity.build();
    }
}
