package com.fpuna.py.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleUpdateRequest extends SaleRequest implements Serializable {

    @JsonProperty(value = "sale_id")
    private Integer saleId;
}

