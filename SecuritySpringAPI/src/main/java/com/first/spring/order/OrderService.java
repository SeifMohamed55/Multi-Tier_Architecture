package com.first.spring.order;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.first.spring.utilities.Loggers;

@Service
public class OrderService {

	@Autowired
	private OrderRepo orderRepo;

	private Logger logger = Loggers.getDBLogger();

	public Order saveOrder(Order order) {
		try {
			return orderRepo.save(order);

		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
		}
		return null;
	}

	public List<Order> getAllOrdersOrderedByDate(String email) {
		try {
			var orders = orderRepo.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
			return orders;
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return List.of();
		}

	}
	
	public boolean cancelOrder(Order order) {
		try {
			order.setStatus(OrderStatus.CANCELED);
			orderRepo.save(order);
			return true;
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return false;
		}

	}
	
	public boolean shipOrder(Order order) {
		try {
			order.setStatus(OrderStatus.SHIPPED);
			orderRepo.save(order);
			return true;
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return false;
		}

	}
	
	public boolean payForOrder(Order order) {
		try {
			order.setStatus(OrderStatus.PAYED);
			orderRepo.save(order);
			return true;
			
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return false;
		}

	}
	
	public List<Order> getOrdersByStatusNewOrderedByUpdatedAt(OrderStatus stat, Long clientId ) {
		var newOrders = orderRepo.findByStatusAndClient_IdOrderByUpdatedAtDesc(stat, clientId);
		if(newOrders.isPresent()) {
			return newOrders.get();			
		}
        return List.of(); 
    }
	
	public Order findOrderById( Long orderId ) {
		var newOrder = orderRepo.findById(orderId);
		if(newOrder.isPresent()) {
			return newOrder.get();			
		}
        return null;
    }
	
	public List<Order> findOrdersByEmail(String email) {
		var newOrder = orderRepo.findByClient_EmailOrderByCreatedAtDesc(email);
		if(newOrder.isPresent()) {
			return newOrder.get();			
		}
        return List.of();
    }

}
