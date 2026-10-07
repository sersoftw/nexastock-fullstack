package com.portfolio.nexastock.order;

import com.portfolio.nexastock.customer.Customer;
import com.portfolio.nexastock.customer.CustomerService;
import com.portfolio.nexastock.exception.ResourceNotFoundException;
import com.portfolio.nexastock.product.Product;
import com.portfolio.nexastock.product.ProductService;
import com.portfolio.nexastock.stock.MovementType;
import com.portfolio.nexastock.stock.StockMovement;
import com.portfolio.nexastock.stock.StockMovementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Year;
import java.util.UUID;

@Service
public class OrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final StockMovementRepository stockMovementRepository;

    public OrderService(
            SalesOrderRepository salesOrderRepository,
            CustomerService customerService,
            ProductService productService,
            StockMovementRepository stockMovementRepository
    ) {
        this.salesOrderRepository = salesOrderRepository;
        this.customerService = customerService;
        this.productService = productService;
        this.stockMovementRepository = stockMovementRepository;
    }

    @Transactional(readOnly = true)
    public Page<OrderResponse> list(Pageable pageable) {
        return salesOrderRepository.findAllByOrderByCreatedAtDesc(pageable).map(OrderResponse::from);
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        SalesOrder order = salesOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));
        return OrderResponse.from(order);
    }

    @Transactional
    public OrderResponse create(OrderRequest request) {
        Customer customer = customerService.findCustomer(request.customerId());
        SalesOrder order = new SalesOrder(generateOrderNumber(), customer);

        BigDecimal total = BigDecimal.ZERO;
        for (OrderLineRequest line : request.items()) {
            Product product = productService.findProduct(line.productId());
            validateStock(product, line.quantity());

            product.setStock(product.getStock() - line.quantity());
            OrderItem item = new OrderItem(product, line.quantity());
            order.addItem(item);
            total = total.add(item.getLineTotal());

            stockMovementRepository.save(new StockMovement(
                    product,
                    MovementType.OUT,
                    line.quantity(),
                    "Pedido " + order.getOrderNumber()
            ));
        }

        order.setTotalAmount(total);
        SalesOrder saved = salesOrderRepository.save(order);
        return OrderResponse.from(saved);
    }

    private void validateStock(Product product, int requestedQuantity) {
        if (product.getStock() < requestedQuantity) {
            throw new IllegalArgumentException(
                    "Stock insuficiente para " + product.getName() + ". Disponible: " + product.getStock()
            );
        }
    }

    private String generateOrderNumber() {
        return "NS-" + Year.now().getValue() + "-" + UUID.randomUUID();
    }
}
