import { Component, OnInit } from '@angular/core';
import { CartService } from '../services/cart.service';
import { UserService } from '../services/user.service';
import { User } from '../shared/models/User';
import { Cart } from '../shared/models/cart';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  cartQuantity = 0;
  user!: User;

  constructor(private cartService: CartService, private userService: UserService) {
    cartService.getCartObservale().subscribe((newCart) => {
      this.cartQuantity = newCart.totalCount;
    })

    userService.userObservable.subscribe((newUser) => {
      this.user = newUser;
    })
  }
  
    ngOnInit(): void {
        
    }  

  logout() {
    this.userService.logout();
  }

  get isAuth(): boolean {
    return !!this.user?.token;
  }

  get isAdmin(): boolean{
    return this.userService.isUserAdmin();
    
  }
}
