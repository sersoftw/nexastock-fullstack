package com.portfolio.nexastock.customer;

import com.portfolio.nexastock.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public Page<CustomerResponse> list(String term, Pageable pageable) {
        String searchTerm = term == null ? "" : term.trim();
        return customerRepository.searchActive(searchTerm, pageable).map(CustomerResponse::from);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        return CustomerResponse.from(findCustomer(id));
    }

    @Transactional
    public CustomerResponse create(CustomerRequest request) {
        if (customerRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Ya existe un cliente con el email " + request.email());
        }
        Customer customer = new Customer(
                request.name().trim(),
                request.email().trim().toLowerCase(),
                clean(request.phone()),
                clean(request.company())
        );
        return CustomerResponse.from(customerRepository.save(customer));
    }

    @Transactional
    public CustomerResponse update(Long id, CustomerRequest request) {
        Customer customer = findCustomer(id);
        customerRepository.findByEmail(request.email().trim().toLowerCase())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Ya existe otro cliente con el email " + request.email());
                });
        customer.setName(request.name().trim());
        customer.setEmail(request.email().trim().toLowerCase());
        customer.setPhone(clean(request.phone()));
        customer.setCompany(clean(request.company()));
        return CustomerResponse.from(customer);
    }

    @Transactional
    public void deactivate(Long id) {
        Customer customer = findCustomer(id);
        customer.setActive(false);
    }

    public Customer findCustomer(Long id) {
        return customerRepository.findById(id)
                .filter(Customer::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
    }

    private String clean(String value) {
        return value == null ? null : value.trim();
    }
}
