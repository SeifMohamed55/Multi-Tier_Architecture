package com.first.spring.order;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.first.spring.clientmodule.Client;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

public class OrderResponseDTO {


	private long id;
			
	private List<OrderItem> items;

	private double totalPrice;
	
	private String name;
	
	private String address;
	
	private LatLng addressLatLng;
		
	private OrderStatus status;
		
	private Date createdAt;
	
	private Date updatedAt;
	
    private Payment payment;
    
    
    
    public OrderResponseDTO(Order order) {
		 this.id = order.getId();
		 this.items = order.getItems();
		 this.totalPrice = order.getTotalPrice();
		 this.name = order.getName();
		 this.address = order.getAddress();
		 this.addressLatLng = order.getAddressLatLng();
		 this.status = order.getStatus();
		 this.createdAt = order.getCreatedAt();
		 this.updatedAt = order.getUpdatedAt();
		 this.payment = order.getPayment();
	 }

    
	 
	 public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LatLng getAddressLatLng() {
		return addressLatLng;
	}

	public void setAddressLatLng(LatLng addressLatLng) {
		this.addressLatLng = addressLatLng;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	

	 
	 
}
