package com.example.template.impl.service.impl;

import com.example.template.api.beans.CouponTemplateInfo;
import com.example.template.api.beans.PagedTemplateInfo;
import com.example.template.api.beans.TemplateSearchParams;
import com.example.template.api.beans.rules.TemplateRule;
import com.example.template.api.enums.CouponType;
import com.example.template.dao.CouponTemplateDao;
import com.example.template.entity.CouponTemplate;
import com.example.template.impl.converter.TemplateConverter;
import com.example.template.impl.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TemplateServiceImpl implements TemplateService {

    @Resource
    private CouponTemplateDao couponTemplateDao;
    @Resource
    private CycleReferenceServiceImpl cycleReferenceService;

    // 克隆优惠券
    @Override
    public CouponTemplateInfo cloneTemplate(Long templateId) {
        cycleReferenceService.get();
        log.info("cloning template id {}", templateId);
        CouponTemplate source = couponTemplateDao.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("invalid template ID"));

        // todo 不copy直接改
        CouponTemplate target = new CouponTemplate();
        BeanUtils.copyProperties(source, target);

        target.setAvailable(true);
        target.setId(null);

        couponTemplateDao.save(target);
        return TemplateConverter.convertToTemplateInfo(target);
    }

    /**
     * 创建优惠券模板
     */
    @Override
    public CouponTemplateInfo createTemplate(CouponTemplateInfo request) {
        // 单个门店最多可以创建100张优惠券模板
        if (request.getShopId() != null) {
            Integer count = couponTemplateDao.countByShopIdAndAvailable(request.getShopId(), true);
            if (count >= 100) {
                log.error("the totals of coupon template exceeds maximum number");
                throw new UnsupportedOperationException("exceeded the maximum of coupon templates that you can create");
            }
        }

        // 创建优惠券
        CouponTemplate template = CouponTemplate.builder()
                .name(request.getName())
                .description(request.getDesc())
                .category(CouponType.convert(request.getType()))
                .available(true)
                .shopId(request.getShopId())
                .rule(request.getRule())
                .build();
        template = couponTemplateDao.save(template);

        return TemplateConverter.convertToTemplateInfo(template);
    }

    @Override
    public PagedTemplateInfo search(TemplateSearchParams request) {
        CouponTemplate example = CouponTemplate.builder()
                .shopId(request.getShopId())
                .category(CouponType.convert(request.getType()))
                .available(request.getAvailable())
                .name(request.getName())
                .build();

        Pageable page = PageRequest.of(request.getPage()-1, request.getPageSize());
        Page<CouponTemplate> result = couponTemplateDao.findAll(Example.of(example), page);
        List<CouponTemplateInfo> couponTemplateInfos = result.stream()
                .map(TemplateConverter::convertToTemplateInfo)
                .collect(Collectors.toList());

        PagedTemplateInfo response = PagedTemplateInfo.builder()
                .templates(couponTemplateInfos)
                .page(request.getPage())
                .total(result.getTotalElements())
                .build();

        return response;
    }

    public List<CouponTemplateInfo> searchTemplate(CouponTemplateInfo request) {
        CouponTemplate example = CouponTemplate.builder()
                .shopId(request.getShopId())
                .category(CouponType.convert(request.getType()))
                .available(request.getAvailable())
                .name(request.getName())
                .build();
        // 可以用下面的方式做分页查询
//        Pageable page = PageRequest.of(0, 100);
//        templateDao.findAll(Example.of(example), page);
        List<CouponTemplate> result = couponTemplateDao.findAll(Example.of(example));
        return result.stream()
                .map(TemplateConverter::convertToTemplateInfo)
                .collect(Collectors.toList());
    }

    /**
     * 通过ID查询优惠券模板
     */
    @Override
    public CouponTemplateInfo loadTemplateInfo(Long id) {
        Optional<CouponTemplate> template = couponTemplateDao.findById(id);
        return template.isPresent() ? TemplateConverter.convertToTemplateInfo(template.get()) : null;
    }

    // 将券无效化
    @Override
    @Transactional
    public void deleteTemplate(Long id) {
        int rows = couponTemplateDao.makeCouponUnavailable(id);
        if (rows == 0) {
            throw new IllegalArgumentException("Template Not Found: " + id);
        }
    }

    /**
     * 批量读取模板
     */
    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids) {

        List<CouponTemplate> templates = couponTemplateDao.findAllById(ids);

        return templates.stream()
                .map(TemplateConverter::convertToTemplateInfo)
                .collect(Collectors.toMap(CouponTemplateInfo::getId, Function.identity()));
    }

    public void fa() {
        saveTemplate();
    }

    @Transactional
    public void saveTemplate() {
        TemplateRule rule = new TemplateRule();
        // 创建优惠券
        CouponTemplate template = CouponTemplate.builder()
                .name("c1")
                .description("test")
                .category(CouponType.UNKNOWN)
                .available(true)
                .shopId(null)
                .rule(rule)
                .build();
        couponTemplateDao.save(template);
        throw new RuntimeException("null");
    }
}
