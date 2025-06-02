package com.fpuna.py.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemResponse implements Serializable {

    @JsonProperty(value = "sale_item_id")
    private Integer saleItemId;
    @JsonProperty(value = "product_id")
    private Integer productId;
    private Integer quantity;
    @JsonProperty(value = "unit_price")
    private BigDecimal unitPrice;
    @JsonProperty(value = "sub_total")
    private BigDecimal subtotal;
}

