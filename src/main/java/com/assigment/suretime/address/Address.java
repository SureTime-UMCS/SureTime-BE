package com.assigment.suretime.address;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    private String city;
    private String street;
    private BigDecimal buildingNumber;
    private BigDecimal apartmentNumber;
}
