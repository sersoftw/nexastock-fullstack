package com.portfolio.nexastock.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
    Optional<Customer> findByEmail(String email);
    long countByActiveTrue();

    @Query("""
            select c from Customer c
            where c.active = true
              and (
                    lower(c.name) like lower(concat('%', :term, '%'))
                 or lower(c.email) like lower(concat('%', :term, '%'))
                 or lower(coalesce(c.company, '')) like lower(concat('%', :term, '%'))
              )
            """)
    Page<Customer> searchActive(@Param("term") String term, Pageable pageable);
}
