package com.assigment.suretime.address;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Address {
    private String city;
    private String street;
    private BigDecimal buildingNumber;
    private BigDecimal apartmentNumber;
}
