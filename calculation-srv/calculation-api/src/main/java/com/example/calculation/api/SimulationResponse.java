package com.example.calculation.api;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;
// 试计算结果
@Data
public class SimulationResponse {
    // 最省钱的coupon
    private Long bestCouponId;

    // 每一个coupon对应的order价格
    private Map<Long, Long> couponToOrderPrice = Maps.newHashMap();
}
