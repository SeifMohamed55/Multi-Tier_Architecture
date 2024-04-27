import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ORDER_CREATE_URL, ORDER_NEW_FOR_CURRENT_USER_URL, ORDER_PAY_URL, ORDER_TRACK_URL, USER_KEY } from '../shared/models/constatns/urls';
import { Order } from '../shared/models/Order';
import { Observable } from 'rxjs';
import { User } from '../shared/models/User';

@Injectable({
  providedIn: 'root'
})
export class OrderService {


  constructor(private http: HttpClient) { }

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

  trackOrderById(id:number): Observable<Order>{
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem("token")}`
      })
    };
    return this.http.get<Order>(ORDER_TRACK_URL + id, httpOptions);
  }

}