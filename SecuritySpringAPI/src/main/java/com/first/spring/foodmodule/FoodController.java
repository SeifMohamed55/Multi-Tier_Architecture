package com.first.spring.foodmodule;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.first.spring.utilities.Loggers;

@RestController
@RequestMapping("/api/food")
public class FoodController {

	@Autowired
	private FoodService foodService;
	
	private Logger logger = Loggers.getControllersLogger();
	

	@GetMapping("")
	public ResponseEntity<Object> getAllFood() {
		var foods = foodService.findAllVisible();
		return ResponseEntity.ok(foods);

	}
	

	@GetMapping("/search/{searchTerm}")
	public ResponseEntity<Object> getFoodsBySearching(@PathVariable("searchTerm") String searchTerm) {
		var foods = foodService.findByNameContaining(searchTerm);
		return ResponseEntity.ok(foods);
	}


	@GetMapping("/tag/{tagName}")
	public ResponseEntity<Object> getFoodsByTag(@PathVariable("tagName") String tagName) {

		var foods = foodService.findByTags(List.of(tagName));
		return ResponseEntity.ok(foods);
	}

	
	// get all tags
	@GetMapping("/tags")
	public ResponseEntity<Object> getAllTags() {

		var tags = foodService.getAllTags();
		return ResponseEntity.ok(tags);
	}
	
	
	// get by selected tags
	@GetMapping("/search/tags")
	public ResponseEntity<Object> getFoodsByTags(@RequestParam List<String> tagNames) {

		var foods = foodService.findByTags(tagNames);
		return ResponseEntity.ok(foods);
	}

	@GetMapping("/origin/{originName}")
	public ResponseEntity<Object> getFoodsByOrigin(@PathVariable("originName") String originName) {

		var foods = foodService.findByOrigins(List.of(originName));
		return ResponseEntity.ok(foods);
	}
	
	
	@GetMapping("/origins")
	public ResponseEntity<Object> getFoodsByOrigin(@RequestParam List<String> originName) {

		var foods = foodService.findByOrigins(originName);
		return ResponseEntity.ok(foods);
	}

	@GetMapping("/id/{foodId}")
	public ResponseEntity<Object> getFoodById(@PathVariable("foodId") String foodId) {
		try {
			var id = Long.parseLong(foodId);
			var food = foodService.findById(id);
			return ResponseEntity.ok(food);
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return ResponseEntity.badRequest().body("Id is unacceptable");
		}

	}
	



	


}
