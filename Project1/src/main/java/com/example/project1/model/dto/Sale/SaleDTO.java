package com.example.project1.model.dto.Sale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SaleDTO {
    private List<SaleProductDAO> saleProducts;
    private Integer costumerId;
}
