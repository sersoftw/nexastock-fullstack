package com.portfolio.nexastock.product;

import com.portfolio.nexastock.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> list(String term, Pageable pageable) {
        String searchTerm = term == null ? "" : term.trim();
        return productRepository.searchActive(searchTerm, pageable).map(ProductResponse::from);
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        return ProductResponse.from(findProduct(id));
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsBySku(request.sku())) {
            throw new IllegalArgumentException("Ya existe un producto con el SKU " + request.sku());
        }
        Product product = new Product(
                request.sku().trim(),
                request.name().trim(),
                request.category().trim(),
                request.price(),
                request.stock(),
                request.minStock()
        );
        return ProductResponse.from(productRepository.save(product));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = findProduct(id);
        productRepository.findBySku(request.sku())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Ya existe otro producto con el SKU " + request.sku());
                });

        product.setSku(request.sku().trim());
        product.setName(request.name().trim());
        product.setCategory(request.category().trim());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setMinStock(request.minStock());
        return ProductResponse.from(product);
    }

    @Transactional
    public void deactivate(Long id) {
        Product product = findProduct(id);
        product.setActive(false);
    }

    public Product findProduct(Long id) {
        return productRepository.findById(id)
                .filter(Product::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
    }
}
