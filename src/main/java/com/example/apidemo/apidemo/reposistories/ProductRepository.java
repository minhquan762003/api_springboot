package com.example.apidemo.apidemo.reposistories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.apidemo.apidemo.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByProductName(String productName);
}
