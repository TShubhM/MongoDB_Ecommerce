package com.mongo.Ecom.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongo.Ecom.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String>{

	Category findByCategoryName(String categoryName);

	List<Category> findByCategoryEnabled(boolean categoryEnabled);


}
