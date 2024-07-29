package com.luv2code.ecommerce.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundResponse {

    private String id;
    private Integer amount;
    private String speed;
}
