package com.example.calculation.impl.service;

import com.example.calculation.api.ShoppingCart;
import com.example.calculation.api.SimulationOrder;
import com.example.calculation.api.SimulationResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface CalculationService {
    ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart cart);

    SimulationResponse simulateOrder(@RequestBody SimulationOrder cart);
}
