package com.portfolio.nexastock.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySku(String sku);
    Optional<Product> findBySku(String sku);
    long countByActiveTrue();

    @Query("""
            select p from Product p
            where p.active = true
              and (
                    lower(p.name) like lower(concat('%', :term, '%'))
                 or lower(p.sku) like lower(concat('%', :term, '%'))
                 or lower(p.category) like lower(concat('%', :term, '%'))
              )
            """)
    Page<Product> searchActive(@Param("term") String term, Pageable pageable);

    @Query("select p from Product p where p.active = true and p.stock <= p.minStock order by p.stock asc")
    List<Product> findLowStock();
}
