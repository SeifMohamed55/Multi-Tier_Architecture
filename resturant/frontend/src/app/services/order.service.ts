import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ALL_ORDERS_FOR_USER, ORDER_CREATE_URL, ORDER_NEW_FOR_CURRENT_USER_URL, ORDER_PAY_URL, ORDER_TRACK_URL, USER_KEY } from '../shared/models/constatns/urls';
import { Order } from '../shared/models/Order';
import { Observable, tap } from 'rxjs';
import { User } from '../shared/models/User';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class OrderService {


  constructor(private http: HttpClient, private toastrService: ToastrService, private router: Router) { }

  create(order:Order){    
    const clientData  =  localStorage.getItem(USER_KEY);
    const client: User = clientData ? JSON.parse(clientData) : null;
    const payload = {
      clientEmail: client.email,
      order: order
    };
    
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem("token")}`,
      })
    };

    return this.http.post<Order>(ORDER_CREATE_URL, payload, httpOptions);
  }

  getNewOrderForCurrentUser():Observable<Order>{
    const clientData  =  localStorage.getItem(USER_KEY);
    const client: User = clientData ? JSON.parse(clientData) : null;
    return this.http.get<Order>(ORDER_NEW_FOR_CURRENT_USER_URL + `/${client.id}`);
  }

  pay(order:Order):Observable<string>{
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem("token")}`
      })
    };
    return this.http.post<string>(ORDER_PAY_URL, order, httpOptions);
  }

  trackOrderById(orderId:string): Observable<Order>{
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem("token")}`
      })
    };
    return this.http.get<Order>(ORDER_TRACK_URL + orderId, httpOptions).pipe(
      tap({
        error: (errorResponse) => {
          this.toastrService.error(errorResponse.error,
            'Order Not Found!')
            this.router.navigate(["/"]);
        }
      })
    );
  }

  getOrdersForCurrentUser(): Observable<Order[]> {
    const clientData = localStorage.getItem(USER_KEY);
    const client: User = clientData ? JSON.parse(clientData) : null;
    return this.http.get<Order[]>(ALL_ORDERS_FOR_USER);
  }

}
