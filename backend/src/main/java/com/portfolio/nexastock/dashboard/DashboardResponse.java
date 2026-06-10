package com.portfolio.nexastock.dashboard;

import com.portfolio.nexastock.order.OrderResponse;
import com.portfolio.nexastock.product.ProductResponse;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponse(
        long activeProducts,
        long activeCustomers,
        long pendingOrders,
        BigDecimal totalRevenue,
        List<ProductResponse> lowStockProducts,
        List<OrderResponse> recentOrders
) {
}
