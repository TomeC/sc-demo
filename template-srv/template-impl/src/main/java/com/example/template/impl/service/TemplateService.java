package com.example.template.impl.service;

import com.example.template.api.beans.CouponTemplateInfo;
import com.example.template.api.beans.PagedTemplateInfo;
import com.example.template.api.beans.TemplateSearchParams;

import java.util.Collection;
import java.util.Map;

public interface TemplateService {
    // 创建优惠券模板
    CouponTemplateInfo createTemplate(CouponTemplateInfo request);

    CouponTemplateInfo cloneTemplate(Long templateId);

    // 模板查询（分页）
    PagedTemplateInfo search(TemplateSearchParams request);

    // 通过模板ID查询优惠券模板
    CouponTemplateInfo loadTemplateInfo(Long id);

    // 让优惠券模板无效
    void deleteTemplate(Long id);

    // 批量查询
    // Map是模板ID，key是模板详情
    Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids);
}
