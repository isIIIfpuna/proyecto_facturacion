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
public class InstallmentResponse implements Serializable {

    @JsonProperty(value = "installment_id")
    private Integer installmentId;
    @JsonProperty(value = "invoice_id")
    private Integer invoiceId;
    @JsonProperty(value = "installment_number")
    private Integer installmentNumber;
    private BigDecimal amount;
    @JsonProperty(value = "due_date")
    private LocalDate dueDate;
    private Boolean paid;
}

