package com.fpuna.py.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest implements Serializable {

    private String address;
    private String email;
    private String name;
    private String phone;
    @JsonProperty(value = "ci_ruc")
    private String ci_ruc;
}

