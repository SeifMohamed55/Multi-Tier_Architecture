import { Food } from "./food";

export class CartItem {
    food: Food;
    quantity: number;
    price: number;
    totalPrice: number;
  
    constructor(food: Food, quantity: number = 1, price: number = food.price, totalPrice: number = quantity * food.price) {
      this.food = food;
      this.quantity = quantity;
      this.price = price;
      this.totalPrice = totalPrice;
    }
  }
