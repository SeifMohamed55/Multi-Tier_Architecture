package com.first.spring.foodmodule;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.first.spring.clientmodule.Client;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Food {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToMany(mappedBy = "favFood")
	@JsonIgnore
	private Set<Client> students = new HashSet<Client>();

	
	@Column(nullable = false, length = 300, unique = true)
	private String name;
	
	@Column(nullable = false)
	private Double price;
	
	@ElementCollection
	private List<String> tags;
	
	
	@Column(nullable = false, length = 200)
	private String imageUrl;
	
	@ElementCollection
	private List<String> origins;
	
	@Column(nullable = false, length = 30)
	private String cookTime;
	
	private boolean hidden = false;

	public Food(long id, String name, Double price, List<String> tags, String imageUrl,
			List<String> origins, String cookTime, boolean hidden) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.tags = tags;
		this.imageUrl = imageUrl;
		this.origins = origins;
		this.cookTime = cookTime;
		this.hidden = hidden;
	}
	
	public Food() {
		
	}

	public long getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}


	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<String> getOrigins() {
		return origins;
	}

	public void setOrigins(List<String> origins) {
		this.origins = origins;
	}

	public String getCookTime() {
		return cookTime;
	}

	public void setCookTime(String cookTime) {
		this.cookTime = cookTime;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	
}

