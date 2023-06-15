package com.example.customer.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.calculation.api.ShoppingCart;
import com.example.calculation.api.SimulationOrder;
import com.example.calculation.api.SimulationResponse;
import com.example.customer.api.beans.RequestCoupon;
import com.example.customer.api.beans.SearchCoupon;
import com.example.customer.api.enums.CouponStatus;
import com.example.customer.dao.entity.Coupon;
import com.example.customer.service.CouponCustomerService;
import com.example.template.api.beans.CouponInfo;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("coupon-customer")
@RefreshScope
public class CouponCustomerController {

    @Resource
    private CouponCustomerService customerService;
//    @Resource
//    private CouponProducer couponProducer;

    @PostMapping("requestCoupon")
    @SentinelResource(value = "requestCoupon", fallback = "getNothing")
    public Coupon requestCoupon(@Valid @RequestBody RequestCoupon request) {
        return customerService.requestCoupon(request);
    }

    public Coupon getNothing(RequestCoupon request) {
        return Coupon.builder()
                .status(CouponStatus.INACTIVE)
                .build();
    }

    // 用户删除优惠券
    @DeleteMapping("deleteCoupon")
    public void deleteCoupon(@RequestParam("userId") Long userId,
                             @RequestParam("couponId") Long couponId) {
        customerService.deleteCoupon(userId, couponId);
    }

    // 用户模拟计算每个优惠券的优惠价格
    @PostMapping("simulateOrder")
    public SimulationResponse simulate(@Valid @RequestBody SimulationOrder order) {
        return customerService.simulateOrderPrice(order);
    }

    // ResponseEntity - 指定返回状态码 - 可以作为一个课后思考题
    @PostMapping("placeOrder")
    public ShoppingCart checkout(@Valid @RequestBody ShoppingCart info) {
        return customerService.placeOrder(info);
    }


    // 实现的时候最好封装一个search object类
    @PostMapping("findCoupon")
    public List<CouponInfo> findCoupon(@Valid @RequestBody SearchCoupon request) {
        return customerService.findCoupon(request);
    }
    // stream
//    @PostMapping("requestCouponEvent")
//    public void requestCouponEvent(@Valid @RequestBody RequestCoupon request) {
//        couponProducer.sendCoupon(request);
//    }
//
//    @PostMapping("requestCouponDelayEvent")
//    public void requestCouponDelayedEvent(@Valid @RequestBody RequestCoupon request) {
//        couponProducer.sendCouponInDelay(request);
//    }
//    // 用户删除优惠券
//    @DeleteMapping("deleteCouponEvent")
//    public void deleteCouponEvent(@RequestParam("userId") Long userId,
//                                  @RequestParam("couponId") Long couponId) {
//        couponProducer.deleteCoupon(userId, couponId);
//    }

    // 用户删除优惠券
    @DeleteMapping("template")
    @GlobalTransactional(name = "coupon-customer-serv", rollbackFor = Exception.class)
    public void deleteTemplate(@RequestParam("templateId") Long templateId) {
        customerService.deleteCouponTemplate(templateId);
    }

}
