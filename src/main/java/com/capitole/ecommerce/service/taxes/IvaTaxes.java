package com.capitole.ecommerce.service.taxes;

import com.capitole.ecommerce.model.Tax;

public class IvaTaxes implements TaxesStrategy {

    @Override
    public Tax getCurrentTax() {
        return Tax.builder().value(18).build();
    }
}
