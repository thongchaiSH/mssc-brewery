package com.ith.msscbrewery.web.services;

import com.ith.msscbrewery.web.model.BeerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {

    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder().id(UUID.randomUUID())
                .beerName("Change")
                .beerStyle("Pale Ale")
                .build();
    }
}
