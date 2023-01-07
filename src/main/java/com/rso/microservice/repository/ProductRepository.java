package com.rso.microservice.repository;

import com.rso.microservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productShops WHERE p.id = :id")
    Optional<Product> findByIdWithProductShops(@Param("id") Long id);
}
