package com.fpuna.py.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fpuna.py.entity.SaleItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequest implements Serializable {

    private CustomerRequest customer;
    @JsonProperty(value = "sale_date")
    private String saleDate;
    @JsonProperty(value = "total_amount")
    private BigDecimal totalAmount;
    @JsonProperty(value = "payment_type")
    private String paymentType;
    @JsonProperty(value = "sale_items")
    private List<SaleItemRequest> saleItems;
}

