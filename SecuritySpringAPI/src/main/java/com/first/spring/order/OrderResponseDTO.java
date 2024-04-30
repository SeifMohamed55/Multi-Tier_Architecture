package com.first.spring.order;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;



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
	
    private String paymentId;
    
    
    
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
		 this.setPaymentId(order.getPaymentId());
	 }

    public static List<OrderResponseDTO> toOrderDTO(List<Order> orders){
    	var dtos = new ArrayList<OrderResponseDTO>();
    	for(Order order : orders) {
    		dtos.add(new OrderResponseDTO(order));
    	}
    	return dtos;
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


	public String getPaymentId() {
		return paymentId;
	}



	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	

	 
	 
}
