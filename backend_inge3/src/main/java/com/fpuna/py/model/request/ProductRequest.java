package com.fpuna.py.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest implements Serializable {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
}

