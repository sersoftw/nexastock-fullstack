package com.portfolio.nexastock.config;

import com.portfolio.nexastock.customer.Customer;
import com.portfolio.nexastock.customer.CustomerRepository;
import com.portfolio.nexastock.order.OrderItem;
import com.portfolio.nexastock.order.SalesOrder;
import com.portfolio.nexastock.order.SalesOrderRepository;
import com.portfolio.nexastock.product.Product;
import com.portfolio.nexastock.product.ProductRepository;
import com.portfolio.nexastock.stock.MovementType;
import com.portfolio.nexastock.stock.StockMovement;
import com.portfolio.nexastock.stock.StockMovementRepository;
import com.portfolio.nexastock.user.AppUser;
import com.portfolio.nexastock.user.Role;
import com.portfolio.nexastock.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(
            UserRepository userRepository,
            ProductRepository productRepository,
            CustomerRepository customerRepository,
            SalesOrderRepository salesOrderRepository,
            StockMovementRepository stockMovementRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (!userRepository.existsByEmail("admin@nexastock.dev")) {
                userRepository.save(new AppUser(
                        "admin@nexastock.dev",
                        passwordEncoder.encode("Admin123!"),
                        "Sergio Admin",
                        Role.ADMIN
                ));
            }

            if (!userRepository.existsByEmail("manager@nexastock.dev")) {
                userRepository.save(new AppUser(
                        "manager@nexastock.dev",
                        passwordEncoder.encode("Manager123!"),
                        "Sergio Manager",
                        Role.MANAGER
                ));
            }

            if (productRepository.count() == 0) {
                productRepository.save(new Product("LAP-DEV-14", "Laptop Developer Pro 14", "Hardware", new BigDecimal("1299.99"), 12, 4));
                productRepository.save(new Product("MON-4K-27", "Monitor 4K 27 pulgadas", "Hardware", new BigDecimal("349.90"), 8, 3));
                productRepository.save(new Product("KEY-MECH-01", "Teclado mecánico compacto", "Periféricos", new BigDecimal("89.50"), 24, 8));
                productRepository.save(new Product("MOU-WL-02", "Ratón inalámbrico ergonómico", "Periféricos", new BigDecimal("39.99"), 30, 10));
                productRepository.save(new Product("SUP-CLOUD-12", "Soporte cloud anual", "Servicios", new BigDecimal("499.00"), 5, 2));
            }

            if (customerRepository.count() == 0) {
                customerRepository.save(new Customer("Acme Digital SL", "compras@acmedigital.test", "+34 600 100 200", "Acme Digital SL"));
                customerRepository.save(new Customer("Nova Retail Group", "it@novaretail.test", "+34 600 300 400", "Nova Retail Group"));
                customerRepository.save(new Customer("BlueTech Consulting", "admin@bluetech.test", "+34 600 500 600", "BlueTech Consulting"));
            }

            if (salesOrderRepository.count() == 0 && customerRepository.count() > 0 && productRepository.count() > 0) {
                Customer acme = customerRepository.findByEmail("compras@acmedigital.test").orElseThrow();
                Product laptop = productRepository.findBySku("LAP-DEV-14").orElseThrow();
                Product monitor = productRepository.findBySku("MON-4K-27").orElseThrow();

                SalesOrder firstOrder = new SalesOrder("NS-2026-000001", acme);
                firstOrder.addItem(new OrderItem(laptop, 1));
                firstOrder.addItem(new OrderItem(monitor, 2));
                firstOrder.setTotalAmount(laptop.getPrice().add(monitor.getPrice().multiply(BigDecimal.valueOf(2))));

                laptop.setStock(laptop.getStock() - 1);
                monitor.setStock(monitor.getStock() - 2);

                productRepository.save(laptop);
                productRepository.save(monitor);
                salesOrderRepository.save(firstOrder);
                stockMovementRepository.save(new StockMovement(laptop, MovementType.OUT, 1, "Pedido demo NS-2026-000001"));
                stockMovementRepository.save(new StockMovement(monitor, MovementType.OUT, 2, "Pedido demo NS-2026-000001"));
            }
        };
    }
}
