package com.example.customer.api.beans;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestCoupon {

    // 用户领券
    @NotNull
    private Long userId;

    // 券模板ID
    @NotNull
    private Long couponTemplateId;

}
