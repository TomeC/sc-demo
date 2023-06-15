package com.example.customer.api.beans;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SearchCoupon {
    @NotNull
    private Long userId;

    private Long shopId;
    private Integer couponStatus;
}
