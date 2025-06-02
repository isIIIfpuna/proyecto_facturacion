package com.fpuna.py.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateRequest extends CustomerRequest implements Serializable {

    @JsonProperty(value = "customer_id")
    private Integer customerId;
}

