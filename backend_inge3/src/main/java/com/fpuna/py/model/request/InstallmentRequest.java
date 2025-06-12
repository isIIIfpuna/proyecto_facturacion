package com.fpuna.py.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentRequest implements Serializable {

    private Integer installments;
    @JsonProperty(value = "installment_days")
    private List<Integer> installmentDays;
}

