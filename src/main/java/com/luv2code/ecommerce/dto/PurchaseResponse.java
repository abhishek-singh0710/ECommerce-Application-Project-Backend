package com.luv2code.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
// @AllArgsConstructor
// @NoArgsConstructor
public class PurchaseResponse {

    private final String orderTrackingNumber;
}
