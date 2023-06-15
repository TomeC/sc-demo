package com.example.template.api.beans;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PagedTemplateInfo {
    public List<CouponTemplateInfo> templates;

    public int page;

    public long total;
}
