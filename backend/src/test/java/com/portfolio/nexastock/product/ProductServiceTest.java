package com.portfolio.nexastock.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createShouldRejectDuplicatedSku() {
        ProductRequest request = new ProductRequest(
                "SKU-001",
                "Producto demo",
                "Demo",
                new BigDecimal("19.99"),
                10,
                2
        );
        when(productRepository.existsBySku("SKU-001")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> productService.create(request));
    }
}
