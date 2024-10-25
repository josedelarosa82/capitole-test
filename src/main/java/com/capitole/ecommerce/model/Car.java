package com.capitole.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString

public class Car {
    private double totalGross;
    private double totalNet;
    private double totalTaxes;
    private double taxRate;
    private int totalItems;
    private List<Item> items;

}