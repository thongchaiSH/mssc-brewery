package com.ith.msscbrewery.web.mappers;

import com.ith.msscbrewery.domain.Customer;
import com.ith.msscbrewery.web.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerDto customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDto customerDto);
}
