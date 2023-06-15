package com.example.customer.feign;

import com.example.calculation.api.ShoppingCart;
import com.example.calculation.api.SimulationOrder;
import com.example.calculation.api.SimulationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "calculation-serv", path = "/calculator")
public interface CalculationServiceClient {
    @PostMapping("simulate")
    SimulationResponse simulate(@RequestBody SimulationOrder order);

    @PostMapping("checkout")
    ShoppingCart checkout(@RequestBody ShoppingCart order);
}
