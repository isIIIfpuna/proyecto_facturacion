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
public class ProductResponse implements Serializable {

    @JsonProperty(value = "product_id")
    private Integer productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
}

