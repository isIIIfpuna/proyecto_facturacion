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
public class SaleResponse implements Serializable {

    @JsonProperty(value = "sale_id")
    private Integer saleId;
    @JsonProperty(value = "customer_id")
    private Integer customerId;
    @JsonProperty(value = "sale_date")
    private String saleDate;
    @JsonProperty(value = "total_amount")
    private BigDecimal totalAmount;
    @JsonProperty(value = "payment_type")
    private String paymentType;
}

