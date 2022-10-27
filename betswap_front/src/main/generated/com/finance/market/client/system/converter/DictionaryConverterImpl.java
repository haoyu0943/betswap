package com.betswap.market.client.system.converter;

import com.betswap.market.client.system.dto.DictionaryDTO;
import com.betswap.market.infrastruture.system.entity.DictionaryEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class DictionaryConverterImpl implements DictionaryConverter {

    @Override
    public DictionaryDTO entity2dto(DictionaryEntity cmd) {
        if ( cmd == null ) {
            return null;
        }

        DictionaryDTO dictionaryDTO = new DictionaryDTO();

        dictionaryDTO.setCode( cmd.getCode() );
        dictionaryDTO.setDicDescribe( cmd.getDicDescribe() );
        dictionaryDTO.setDicType( cmd.getDicType() );
        dictionaryDTO.setDicTypeDescribe( cmd.getDicTypeDescribe() );

        return dictionaryDTO;
    }
}
