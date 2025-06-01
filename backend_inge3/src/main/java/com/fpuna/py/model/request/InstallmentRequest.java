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
public class InstallmentRequest implements Serializable {

    @JsonProperty(value = "invoice_id")
    private Integer invoiceId;
    @JsonProperty(value = "installment_number")
    private Integer installmentNumber;
    private BigDecimal amount;
    @JsonProperty(value = "due_date")
    private String dueDate;
}

