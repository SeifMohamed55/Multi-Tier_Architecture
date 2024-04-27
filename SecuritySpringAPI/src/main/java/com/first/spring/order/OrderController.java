package com.first.spring.order;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.first.spring.clientmodule.Client;
import com.first.spring.clientmodule.ClientService;
import com.first.spring.loginmodule.UserDTO;
import com.first.spring.utilities.Loggers;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private OrderService orderService;
	
	private Logger logger = Loggers.getControllersLogger();

	@PostMapping("/create")
	public ResponseEntity<Object> createOrder(@RequestBody OrderPayload payload){
		try {
			Order order = payload.getOrder();
			String clientEmail = payload.getClientEmail();
			Client client = clientService.getClientByEmail(clientEmail); 

			Date date = new Date(Calendar.getInstance().getTime().getTime());
			order.setUpdatedAt(date);
			order.setCreatedAt(date);
			order.setStatus(OrderStatus.NEW);
			order.setUser(client);
			
			for (OrderItem item : order.getItems()) {
		        item.setOrder(order);
		    }
			order = orderService.saveOrder(order);
			
			OrderResponseDTO response = new OrderResponseDTO(order);
			return ResponseEntity.ok(response);
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return ResponseEntity.badRequest().body("Order Structure is unaccepted");
		}
	}
	
	@GetMapping("/newOrderForCurrentUser/{id}")
	public ResponseEntity<Object> getNewOrder(@PathVariable("id") Long id){
		List<Order> order = orderService.getOrdersByStatusNewOrderedByUpdatedAt(OrderStatus.NEW, id);
		if(order.size() > 0) {
			return ResponseEntity.ok(order.getFirst());
		}
		return ResponseEntity.ok(List.of());
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
