package com.first.spring.foodmodule;

public class Tag {
	private String name;
	private long count;
	
	

	public Tag() {
		
	}
	
	public Tag(String name, long count) {
		this.name = name;
		this.count = count;
	}
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "name: " + name + "{ "+ count +" }";
	}
}
