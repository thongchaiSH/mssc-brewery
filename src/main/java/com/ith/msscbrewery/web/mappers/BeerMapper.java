package com.ith.msscbrewery.web.mappers;

import com.ith.msscbrewery.domain.Beer;
import com.ith.msscbrewery.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
