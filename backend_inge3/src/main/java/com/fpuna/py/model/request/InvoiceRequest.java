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
public class InvoiceRequest implements Serializable {

    @JsonProperty(value = "customer_id")
    private Integer customerId;
    private String date;
    @JsonProperty(value = "total_amount")
    private BigDecimal totalAmount;
    @JsonProperty(value = "payment_type")
    private String paymentType;
    @JsonProperty(value = "installment_count")
    private Integer installmentCount;
    @JsonProperty(value = "installment_days")
    private String installmentDays;
    private String status;
}

