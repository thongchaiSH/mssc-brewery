package com.ith.msscbrewery.web.controller.v2;


import com.ith.msscbrewery.web.model.v2.BeerDtoV2;
import com.ith.msscbrewery.web.services.v2.BeerServiceV2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v2/beer")
@RestController
public class BeerControllerV2 {


    private final BeerServiceV2 beerServiceV2;

    public BeerControllerV2(BeerServiceV2 beerService) {
        this.beerServiceV2 = beerService;
    }

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDtoV2> getBeer(@PathVariable UUID beerId){
        return new ResponseEntity<>(beerServiceV2.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping //Post - create new beer
    public ResponseEntity handlePost(BeerDtoV2 BeerDtoV2){
        BeerDtoV2 saveDto=beerServiceV2.saveNewBeer(BeerDtoV2);

        HttpHeaders headers=new HttpHeaders();
        //todo add hostname to utl
        headers.add("Location","/api/v1/beer/"+saveDto.getId().toString());

        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @PutMapping({"/{beerId}"})
    public ResponseEntity handleUpdate(@PathVariable UUID beerId,@RequestBody BeerDtoV2 BeerDtoV2){

        beerServiceV2.updateBeer(beerId,BeerDtoV2);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable UUID beerId){
        beerServiceV2.deleteBeerById(beerId);
    }
}
