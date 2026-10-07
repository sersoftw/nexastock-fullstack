package com.portfolio.nexastock.order;

import com.portfolio.nexastock.customer.Customer;
import com.portfolio.nexastock.customer.CustomerService;
import com.portfolio.nexastock.product.Product;
import com.portfolio.nexastock.product.ProductService;
import com.portfolio.nexastock.stock.StockMovementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private SalesOrderRepository salesOrderRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private ProductService productService;
    @Mock
    private StockMovementRepository stockMovementRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createShouldRejectWhenStockIsInsufficient() {
        Customer customer = new Customer("Cliente", "cliente@test.dev", null, null);
        Product product = new Product("SKU-LOW", "Producto bajo stock", "Demo", new BigDecimal("10.00"), 1, 1);
        OrderRequest request = new OrderRequest(1L, List.of(new OrderLineRequest(10L, 2)));

        when(customerService.findCustomer(1L)).thenReturn(customer);
        when(productService.findProduct(10L)).thenReturn(product);

        assertThrows(IllegalArgumentException.class, () -> orderService.create(request));
    }

    @Test
void createShouldGenerateDifferentReferencesWithoutPersistedCountChanging() {
    Customer customer = new Customer(
            "Cliente", "cliente@test.dev", null, null
    );
    Product product = new Product(
            "SKU-REF", "Producto", "Demo",
            new BigDecimal("10.00"), 10, 1
    );
    OrderRequest request = new OrderRequest(
            1L, List.of(new OrderLineRequest(10L, 1))
    );

    when(customerService.findCustomer(1L)).thenReturn(customer);
    when(productService.findProduct(10L)).thenReturn(product);
    when(salesOrderRepository.save(any(SalesOrder.class)))
            .thenAnswer(invocation ->
                    invocation.getArgument(0, SalesOrder.class));

    OrderResponse first = orderService.create(request);
    OrderResponse second = orderService.create(request);

    assertNotEquals(first.orderNumber(), second.orderNumber());
    }
}
