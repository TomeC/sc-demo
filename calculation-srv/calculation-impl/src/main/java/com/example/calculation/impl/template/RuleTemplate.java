package com.example.calculation.impl.template;

import com.example.calculation.api.ShoppingCart;

public interface RuleTemplate {
    // 计算优惠券
    ShoppingCart calculate(ShoppingCart settlement);
}
