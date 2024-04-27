package com.first.spring.order;


public class OrderPayload {
	
    
	private String clientEmail;
	private Order order;
        
    public OrderPayload(String clientEmail, Order order) {
		this.clientEmail = clientEmail;
		this.order = order;
	}
    
    public OrderPayload() {
    	
    }
    
    
	public String getClientEmail() {
		return clientEmail;
	}
	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}

    // Getters and setters
}