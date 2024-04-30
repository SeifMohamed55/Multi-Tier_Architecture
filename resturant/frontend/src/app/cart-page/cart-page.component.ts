import { Component, OnInit } from '@angular/core';
import { Cart } from '../shared/models/cart';
import { CartService } from '../services/cart.service';
import { CartItem } from '../shared/models/cartItem';

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.css']
})
export class CartPageComponent implements OnInit {
  cart!: Cart;

  constructor(private cartService: CartService){
    this.cartService.getCartObservale().subscribe((cart) =>{
      this.cart = cart;
      this.updateTotalPrice(); // Update total prices when cart changes
    })
  }

  ngOnInit(): void {
      
  }

  removeFromCart(cartItem: CartItem){
    this.cartService.removeFromCart(cartItem.food.id);
    this.updateTotalPrice(); // Update total prices after removing item
  }

  changeQuantity(cartItem: CartItem, quantityInString: string){
    const quantity = parseInt(quantityInString);
    this.cartService.changeQuantity(cartItem.food.id, quantity);
    this.updateTotalPrice(); // Update total prices after changing quantity
  }

  updateTotalPrice() {
    if (this.cart) {
      this.cart.items.forEach(item => {
        item.totalPrice = item.quantity * item.price;
      });
      this.cart.totalPrice = this.cart.items.reduce((total, item) => total + item.totalPrice, 0);
    }
  }

}
