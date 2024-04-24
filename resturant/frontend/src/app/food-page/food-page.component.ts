import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FoodService } from '../services/food.service';
import { Food } from '../shared/models/food';
import { CartService } from '../services/cart.service';

@Component({
  selector: 'app-food-page',
  templateUrl: './food-page.component.html',
  styleUrl: './food-page.component.css'
})
export class FoodPageComponent implements OnInit {
  food: Food | undefined;

  constructor(activatedRoute: ActivatedRoute, foodService: FoodService, private cartService: CartService, private router: Router){
    activatedRoute.params.subscribe((params) => {
      if(params.id)
      foodService.getFoodById(params.id).subscribe(serverFood => {
        this.food = serverFood;
       })
    })
  }

  ngOnInit(): void {
      
  }

  addToCart(){
    if (this.food) {
      this.cartService.addToCart(this.food);
      this.router.navigateByUrl('/cart-page');
    } else {
      // Handle the case where this.food is undefined
      console.error("Food item is undefined.");
    }
  }
  

}
