package com.first.spring.order;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.first.spring.clientmodule.Client;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "food_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> items;

	private double totalPrice;
	
	private String name;
	
	private String address;
	
	@Embedded
	private LatLng addressLatLng;
		
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
    @JsonIgnoreProperties("orders")
	private Client client;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	@OneToOne
    private Payment payment;
	
	public Order(long id, List<OrderItem> items, double totalPrice, String name, String address, LatLng addressLatLng,
			long paymentId, OrderStatus status, Client client, Date createdAt, Date updatedAt) {
		this.id = id;
		this.items = items;
		this.totalPrice = totalPrice;
		this.name = name;
		this.address = address;
		this.addressLatLng = addressLatLng;
		//this.paymentId = paymentId;
		this.status = status;
		this.client = client;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Order() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
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

//	public long getPaymentId() {
//		return paymentId;
//	}
//
//	public void setPaymentId(long paymentId) {
//		this.paymentId = paymentId;
//	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Client getUser() {
		return client;
	}

	public void setUserId(Client client) {
		this.client = client;
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

}
