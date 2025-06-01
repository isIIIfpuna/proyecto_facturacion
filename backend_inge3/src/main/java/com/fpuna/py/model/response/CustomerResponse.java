package com.fpuna.py.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse implements Serializable {

    @JsonProperty(value = "customer_id")
    private Integer customerId;
    private String address;
    private String email;
    private String name;
    private String phone;
    @JsonProperty(value = "ci_ruc")
    private String ciRuc;
}

