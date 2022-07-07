package com.mongo.Ecom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongo.Ecom.exception.EmptyInputException;
import com.mongo.Ecom.exception.NoSuchObjectExistException;
import com.mongo.Ecom.exception.ObjectNotFoundException;
import com.mongo.Ecom.model.Category;
import com.mongo.Ecom.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository catRepo;

	public List<Category> findAll() {
		List<Category> list = catRepo.findAll();
		if (list.isEmpty()) {
			throw new RuntimeException("The category list you are requesting is empty.");
		} else {
			return list;
		}
	}

	public Category getCategoryById(String id) {
		Category cat = catRepo.findById(id).orElse(null);
		if (cat == null) {
			throw new EmptyInputException("404", "The Category you want to retrieve is not present");
		} else {
			return cat;
		}

	}

	public Category getCategoryByCategoryName(String categoryName) {
		Category catFound = catRepo.findByCategoryName(categoryName);
		if (catFound != null) {
			return catFound;
		} else {
			throw new ObjectNotFoundException("404", "Category with this name " + categoryName + " is not present");
		}
	}

	public List<Category> getAllCategoryByCategoryEnabled(boolean categoryStatus) {
		List<Category> list = catRepo.findByCategoryEnabled(categoryStatus);
		if (list.isEmpty()) {
			throw new RuntimeException("The Category list you are requesting is empty.");
		} else {
			return list;
		}
	}

	public Category addCategory(Category category) {
		if (category.getId().isEmpty() || category.getCategoryName().isEmpty()) {
			throw new EmptyInputException("204", "Input field is Empty, Please check again.");
		}
		Category existingCat = catRepo.findById(category.getId()).orElse(null);
		if (existingCat == null) {
			Category catFound = catRepo.insert(category);
			return catFound;
		} else {
			throw new ObjectNotFoundException("400", "Category with id " + category.getId() + " And Name "
					+ category.getCategoryName() + " Already Exist.");
		}
	}

	public Category updateCategoryById(Category category) {
		Category catFound = catRepo.findById(category.getId()).orElse(null);
		if (catFound == null) {
			throw new NoSuchObjectExistException("400", "Bad Request. Category Requesting is not present");
		} else {
			catFound.setCategoryName(category.getCategoryName());
			catFound.setCategoryEnabled(category.isCategoryEnabled());
			catRepo.save(catFound);
			return catFound;
		}
	}

	public String deleteCategoryById(String id) {
		Category catFound = catRepo.findById(id).orElse(null);
		if (catFound != null) {
			catRepo.delete(catFound);
			return "Category Deleted Successfully";
		} else {
			throw new RuntimeException("Category You want to delete is not present.Please check again.");
		}
	}

	public String deleteCategoryByName(String categoryName) {
		Category catFouond = catRepo.findByCategoryName(categoryName);
		if (catFouond == null) {
			throw new RuntimeException("Category you want to delete is not persent. please check again.");
		} else {
			catRepo.delete(catFouond);
			return "Category deleted Successfully";
		}
	}

	public String deleteCategoryByCategoryEnabled(boolean categoryEnabled) {
		List<Category> catFound = catRepo.findByCategoryEnabled(categoryEnabled);
		if (catFound == null) {
			throw new RuntimeException("Category you want to deleted is not present. please check again.");
		} else {
			catRepo.deleteAll(catFound);
			return "Category deleted Successfully";
		}
	}

}
