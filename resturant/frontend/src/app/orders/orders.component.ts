import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Order } from '../shared/models/Order';
import { OrderService } from '../services/order.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  orders: Order[] = [];

  constructor(private orderService: OrderService, private router: Router) { }

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders() {
    this.orderService.getOrdersForCurrentUser().subscribe(
      orders => {
        this.orders = orders;
      },
      error => {
        console.error('Failed to load orders:', error);
      }
    );
  }

  navigateToOrderDetails(orderId: number) {
    this.router.navigate(['track', orderId]); 
  }
}
