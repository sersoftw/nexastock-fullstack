package com.portfolio.nexastock.dashboard;

import com.portfolio.nexastock.customer.CustomerRepository;
import com.portfolio.nexastock.order.OrderResponse;
import com.portfolio.nexastock.order.OrderStatus;
import com.portfolio.nexastock.order.SalesOrderRepository;
import com.portfolio.nexastock.product.ProductRepository;
import com.portfolio.nexastock.product.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final SalesOrderRepository salesOrderRepository;

    public DashboardService(
            ProductRepository productRepository,
            CustomerRepository customerRepository,
            SalesOrderRepository salesOrderRepository
    ) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.salesOrderRepository = salesOrderRepository;
    }

    @Transactional(readOnly = true)
    public DashboardResponse getDashboard() {
        return new DashboardResponse(
                productRepository.countByActiveTrue(),
                customerRepository.countByActiveTrue(),
                salesOrderRepository.countByStatus(OrderStatus.DRAFT),
                salesOrderRepository.totalRevenueExcluding(OrderStatus.CANCELLED),
                productRepository.findLowStock().stream().map(ProductResponse::from).limit(5).toList(),
                salesOrderRepository.findTop5ByOrderByCreatedAtDesc().stream().map(OrderResponse::from).toList()
        );
    }
}
