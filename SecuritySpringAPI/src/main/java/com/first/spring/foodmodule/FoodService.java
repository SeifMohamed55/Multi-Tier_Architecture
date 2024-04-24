package com.first.spring.foodmodule;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.first.spring.utilities.Loggers;

@Service
public class FoodService {

	@Autowired
	private FoodRepository foodRepo;

	private Logger logger = Loggers.getDBLogger();

	public List<Food> findByNameContaining(String name) {

		var foods = foodRepo.findByNameContainingAndHiddenFalseOrderByName(name);
		if (foods.isPresent()) {
			return foods.get();
		} else {
			return List.of();
		}

	}

	public List<Food> findByPriceLessThanEqual(Double price) {

		var foods = foodRepo.findByPriceLessThanEqualAndHiddenFalse(price);
		if (foods.isPresent()) {
			return foods.get();
		} else {
			return List.of();
		}

	}
	
	public List<Food> findByCookTimeContaining(String cookTime) {

		var foods = foodRepo.findByCookTimeContainingAndHiddenFalse(cookTime);
		if (foods.isPresent()) {
			return foods.get();
		} else {
			return List.of();
		}

	}
	
	public List<Food> findByOrigins(List<String> origins) {

		var foods = foodRepo.findByOriginsAndHiddenFalse(origins);
		if (foods.isPresent()) {
			return foods.get();
		} else {
			return List.of();
		}

	}
	
	public List<Food> findByTags(List<String> tags) {

		var foods = foodRepo.findByTagsAndHiddenFalse(tags);
		if (foods.isPresent()) {
			return foods.get();
		} else {
			return List.of();
		}

	}
	
	public List<Food> findAll(){
		try {
			var foods = foodRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
			return foods;
		}catch(Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return List.of();
		}
		 
	}
	
	public Food findById(Long id){
		try {
			var foods = foodRepo.findById(id);
			return foods.get();
		}catch(Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return null;
		}
		 
	}
	
	
	public Food save(Food food){
		try {
			var foodSaved = foodRepo.save(food);
			return foodSaved;
		}catch(Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return null;
		}
		 
	}
	
	public boolean hideFood(Food food) {
		try {
			food.setHidden(true);
			foodRepo.save(food);
			return true;
		}catch(Exception ex) {
			return false;
		}
		
	}
	
	public boolean unhideFood(Food food) {
		try {
			food.setHidden(false);
			foodRepo.save(food);
			return true;
		}catch(Exception ex) {
			return false;
		}
		
	}
	
	public List<Food> getAllHidden(){
		var foods = foodRepo.findByHiddenTrueOrderByName();
		if(foods.isPresent()) {
			return foods.get();
		}
		return List.of();
	}
	
	public List<Food> findAllVisible(){
		var foods = foodRepo.findByHiddenFalseOrderByName();
		if(foods.isPresent()) {
			return foods.get();
		}
		return List.of();
	}
	
	public List<Tag> getAllTags(){
		
		 var tags = foodRepo.getAllTags();
		 
		 var finaleTags = new ArrayList<Tag>();
		 
		 if(tags.isPresent()) {
			 var presentTags = tags.get();
			 for(Object[] tag : presentTags) {
				 finaleTags.add(new Tag((String)tag[0], (long)tag[1]));
			 }
			 return finaleTags;
			 
		 }
		 return List.of();
	}
	
	
}
