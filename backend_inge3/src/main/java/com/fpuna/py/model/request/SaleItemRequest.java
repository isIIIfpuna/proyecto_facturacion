package com.fpuna.py.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemRequest implements Serializable {

    @JsonProperty(value = "product_id")
    private Integer productId;
    private Integer quantity;
    @JsonProperty(value = "unit_price")
    private BigDecimal unitPrice;
    @JsonProperty(value = "sub_total")
    private BigDecimal subtotal;
}

