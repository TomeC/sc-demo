package com.example.customer.dao;

import com.example.customer.api.enums.CouponStatus;
import com.example.customer.dao.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponDao extends JpaRepository<Coupon, Long> {
    Long countByUserIdAndTemplateId(Long userId, Long couponTemplateId);

    @Modifying
    @Query("update Coupon c set c.status = :status where c.templateId = :templateId")
    void deleteCouponInBatch(@Param("templateId") Long templateId,  @Param("status") CouponStatus inactive);
}
