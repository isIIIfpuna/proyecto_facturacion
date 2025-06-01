package com.fpuna.py.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse implements Serializable {

    @JsonProperty(value = "invoice_id")
    private Integer invoiceId;
    @JsonProperty(value = "customer_id")
    private Integer customerId;
    private LocalDate date;
    @JsonProperty(value = "total_amount")
    private BigDecimal totalAmount;
    @JsonProperty(value = "payment_type")
    private String paymentType;
    @JsonProperty(value = "installment_count")
    private Integer installmentCount;
    private String status;
}

