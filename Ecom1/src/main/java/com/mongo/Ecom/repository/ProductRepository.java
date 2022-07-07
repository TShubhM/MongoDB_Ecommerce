package com.mongo.Ecom.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongo.Ecom.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

	List<Product> findByBrandId(String brandId);

	Product findByName(String name);


}
