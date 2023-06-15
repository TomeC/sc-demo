package com.example.customer.conf;

import com.example.customer.dao.entity.Coupon;
import com.example.template.api.beans.CouponInfo;

public class CouponConverter {

    public static CouponInfo convertToCoupon(Coupon coupon) {

        return CouponInfo.builder()
                .id(coupon.getId())
                .status(coupon.getStatus().getCode())
                .templateId(coupon.getTemplateId())
                .shopId(coupon.getShopId())
                .userId(coupon.getUserId())
                .templateInfo(coupon.getTemplateInfo())
                .build();
    }
}
