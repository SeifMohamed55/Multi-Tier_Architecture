package com.first.spring.clientmodule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.first.spring.authmodule.Authority;
import com.first.spring.foodmodule.Food;
import com.first.spring.order.Order;
import com.first.spring.refreshtoken.RefreshToken;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;

@Entity
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "client_id")
	private long id;

	@Column(nullable = false, length = 30)
	private String firstName;

	@Column(nullable = false, length = 30)
	private String lastName;

	@Column(nullable = false, length = 50)
	private String address;

	@Column(nullable = false, length = 50, unique = true)
	private String email;

	@Column(nullable = false, length = 100)
	private String password;

	@Column(nullable = false)
	private boolean banned = false;

	@Version
	private Integer version;

	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("client")
	private List<Order> orders;

	@ManyToMany
	@JoinTable(name = "client_authority", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "auth_id"))
	private Set<Authority> authorities = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "client_food_fav", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "food_id"))
	private Set<Food> favFood = new HashSet<>();



	public Client(Long studentId, String firstName, String lastName, String email, String password, String address) {
		this.id = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.address = address;
	}

	public Client() {

	}

	@Override
	public String toString() {
		String authority = "";
		for (Authority auth : authorities) {
			authority += auth.getAuthority();
		}
		return "ClientId: " + id + "\nemail: " + email + "\nAuthorities: " + authority;
	}

	public long getId() {
		return id;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public void addAuthority(Authority authority) {
		this.authorities.add(authority);
	}

	public Set<Authority> getAuthorities() {
		return this.authorities;
	}

	public void changeCurrentFields(Client student) {
		this.id = student.id;
		this.firstName = student.firstName;
		this.lastName = student.lastName;
		this.email = student.email;
		this.password = student.password;
	}

	public boolean hasNullField() {
		if (firstName == null || lastName == null || email == null || password == null) {
			return true;
		}
		return false;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public Set<Food> getFavFood() {
		return favFood;
	}

	public void setFavFood(Set<Food> favFood) {
		this.favFood = favFood;
	}
	

}
