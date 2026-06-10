package com.portfolio.nexastock.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    long countByStatus(OrderStatus status);

    @Query("select coalesce(sum(o.totalAmount), 0) from SalesOrder o where o.status <> :cancelled")
    BigDecimal totalRevenueExcluding(@Param("cancelled") OrderStatus cancelled);

    Page<SalesOrder> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<SalesOrder> findTop5ByOrderByCreatedAtDesc();
}
