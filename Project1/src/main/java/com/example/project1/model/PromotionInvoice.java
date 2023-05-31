package com.example.project1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PromotionInvoice extends Invoice{
    private static final Boolean isPromotion=true;
    public PromotionInvoice(Invoice invoice) {
        super(invoice.getId(), invoice.getCostumer(), invoice.getSale());
    }


}
