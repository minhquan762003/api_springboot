package com.example.apidemo.apidemo.database;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.apidemo.apidemo.models.Product;
import com.example.apidemo.apidemo.reposistories.ProductRepository;


/*
 * doker run -d --rm --name mysql-spring-boot-tutorial
 * 
 */
@Configuration
public class Database {

    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository){
        

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Product productA  = new Product( "Macbook pro", 2020, 2400.0, "");
                Product productB  = new Product( "ipad", 2010, 599.0, "");
                logger.info("insert data: " + productRepository.save(productA));
                logger.info("insert data: " + productRepository.save(productB));
                
            }
            
        };
    }
}
