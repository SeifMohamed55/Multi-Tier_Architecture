package com.first.spring.order;

import java.util.List;

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

	public List<Order> getAllOrdersOrderedByDate() {
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
			if(order.getTotalPrice() > order.getPayment().getAmount().doubleValue()) 
				throw new Exception();
			order.setStatus(OrderStatus.PAYED);
			orderRepo.save(order);
			return true;
			
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return false;
		}

	}
	
	

}
