package com.example.customer.service;

import com.example.calculation.api.ShoppingCart;
import com.example.calculation.api.SimulationOrder;
import com.example.calculation.api.SimulationResponse;
import com.example.customer.api.beans.RequestCoupon;
import com.example.customer.api.beans.SearchCoupon;
import com.example.customer.dao.entity.Coupon;
import com.example.template.api.beans.CouponInfo;

import java.util.List;

public interface CouponCustomerService {

    // 领券接口
    Coupon requestCoupon(RequestCoupon request);

    // 核销优惠券
    ShoppingCart placeOrder(ShoppingCart info);

    // 优惠券金额试算
    SimulationResponse simulateOrderPrice(SimulationOrder order);

    void deleteCoupon(Long userId, Long couponId);

    // 查询用户优惠券
    List<CouponInfo> findCoupon(SearchCoupon request);

    void deleteCouponTemplate(Long templateId);
}
