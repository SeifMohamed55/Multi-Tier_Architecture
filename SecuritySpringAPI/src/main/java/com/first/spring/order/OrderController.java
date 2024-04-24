package com.first.spring.order;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.first.spring.utilities.Loggers;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	private Logger logger = Loggers.getControllersLogger();

	@PostMapping("/create")
	public ResponseEntity<Object> createOrder(@RequestBody String entity){
		ObjectMapper mapper = new ObjectMapper();
		Order order;
		try {
			order = mapper.readValue(entity, Order.class);
			order.setStatus(OrderStatus.NEW);
			var details = orderService.saveOrder(order);
			return ResponseEntity.ok(details);
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return ResponseEntity.badRequest().body("Order Structure is unaccepted");
		}
	}
	
	@PostMapping("/pay")
	public ResponseEntity<Object> payForOrder(@RequestBody String entity){
		ObjectMapper mapper = new ObjectMapper();
		Order order;
		try {
			order = mapper.readValue(entity, Order.class);
			order.setStatus(OrderStatus.PAYED);
			var details = orderService.saveOrder(order);
			return ResponseEntity.ok(details);
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return ResponseEntity.badRequest().body("Order Structure is unaccepted");
		}
	}
	
	@PostMapping("/cancel")
	public ResponseEntity<Object> cancelOrder(@RequestBody String entity){
		ObjectMapper mapper = new ObjectMapper();
		Order order;
		try {
			order = mapper.readValue(entity, Order.class);
			order.setStatus(OrderStatus.CANCELED);
			var details = orderService.saveOrder(order);
			return ResponseEntity.ok(details);
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return ResponseEntity.badRequest().body("Order Structure is unaccepted");
		}
	}
}
