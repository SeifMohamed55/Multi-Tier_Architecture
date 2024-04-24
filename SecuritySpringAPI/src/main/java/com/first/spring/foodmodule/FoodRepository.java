package com.first.spring.foodmodule;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

	Optional<List<Food>> findByNameContainingAndHiddenFalseOrderByName(String name);

	Optional<List<Food>> findByPriceLessThanEqualAndHiddenFalse(Double price);

	Optional<List<Food>> findByCookTimeContainingAndHiddenFalse(String cookTime);

	@Query("SELECT DISTINCT f FROM Food f JOIN f.tags t WHERE t IN :tags AND f.hidden = false ORDER BY f.name")
	Optional<List<Food>> findByTagsAndHiddenFalse(List<String> tags);

	@Query("SELECT DISTINCT f FROM Food f JOIN f.origins o WHERE o IN :origins AND f.hidden = false ORDER BY f.name")
	Optional<List<Food>> findByOriginsAndHiddenFalse(List<String> origins);

	Optional<List<Food>> findByHiddenTrueOrderByName();
	
	Optional<List<Food>> findByHiddenFalseOrderByName();
	
    @Query("SELECT t, COUNT(t) FROM Food f JOIN f.tags t WHERE f.hidden = false GROUP BY t")
    Optional<List<Object[]>> getAllTags();
}
