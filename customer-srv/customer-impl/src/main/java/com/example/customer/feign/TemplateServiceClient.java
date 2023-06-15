package com.example.customer.feign;

import com.example.template.api.beans.CouponTemplateInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Map;

@FeignClient(value = "template-serv", path = "/template")
public interface TemplateServiceClient {
    @GetMapping("/getTemplate")
    CouponTemplateInfo getTemplate(@RequestParam("id") Long id);

    @GetMapping("/getBatch")
    Map<Long, CouponTemplateInfo> getBatch(@RequestParam("ids") Collection<Long> ids);

    @DeleteMapping("/deleteTemplate")
    void deleteTemplate(@RequestParam("id") Long id);
}
