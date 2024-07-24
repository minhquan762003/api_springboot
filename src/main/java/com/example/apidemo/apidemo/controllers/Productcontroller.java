package com.example.apidemo.apidemo.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apidemo.apidemo.models.Product;
import com.example.apidemo.apidemo.models.ResponseObject;
import com.example.apidemo.apidemo.reposistories.ProductRepository;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class Productcontroller {

    @Autowired
    private ProductRepository repository;

    @GetMapping("")
    //http:\\localhost:8080/api/v1/Products
    List<Product> getAllProducts(){
        return repository.findAll();
    }
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id){
        Optional<Product> foundProduct = repository.findById(id);
        return foundProduct.isPresent() ?
            ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query product successfully", foundProduct)
            ):
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("false", "Cannot find product id with id = " + id, "")
            );

    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct){

        List<Product> foundProducts = repository.findByProductName(newProduct.getProductName().trim());

        if(foundProducts.size() > 0){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("failed", "Product name already taken", "")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok", "Insert product successfully", repository.save(newProduct))
        );

    }
    //upsert

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id){
        Product updatedProduct =  repository.findById(id)
            .map(product -> {
                product.setProductName(newProduct.getProductName());
                product.setPrice(newProduct.getPrice());
                product.setUrl(newProduct.getUrl());
                product.setproductYear(newProduct.getproductYear());
                return repository.save(product);
            }).orElseGet(() -> {
                newProduct.setId(id);
                return repository.save(newProduct);
            });
        return ResponseEntity.status(HttpStatus.OK).body(
          new ResponseObject("ok", "Update product successfully", updatedProduct)  
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id){
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Delete product successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new ResponseObject("failed", "Cannot find product to delete", "")
        );
    }
}
