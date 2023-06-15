package com.example.template.api.beans;

import com.example.template.api.beans.rules.TemplateRule;
import com.sun.istack.internal.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CouponTemplateInfo {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String desc;

    @NotNull
    private String type;

    private Long shopId;

    @NotNull
    private TemplateRule rule;

    private Boolean available;
}
