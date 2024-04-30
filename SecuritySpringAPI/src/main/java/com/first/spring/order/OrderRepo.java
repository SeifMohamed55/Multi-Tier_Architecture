package com.first.spring.order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{
	
	Optional<List<Order>> findByStatusAndClient_IdOrderByUpdatedAtDesc(OrderStatus status, Long clientId);
	Optional<List<Order>> findByClient_EmailOrderByCreatedAtDesc(String email);
	
	
	
}
