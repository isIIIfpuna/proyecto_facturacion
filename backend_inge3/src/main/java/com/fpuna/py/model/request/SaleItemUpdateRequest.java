package com.fpuna.py.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemUpdateRequest extends SaleItemRequest implements Serializable {

    @JsonProperty(value = "sale_item_id")
    private Integer saleItemId;
}

