import { Component, OnInit } from '@angular/core';
import { Food } from '../shared/models/food';
import { FoodService } from '../services/food.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-admin-foods',
  templateUrl: './admin-foods.component.html',
  styleUrl: './admin-foods.component.css'
})
export class AdminFoodsComponent implements OnInit {


  foods: Food[] = [];

  constructor(private foodService: FoodService, private router: Router) { }

  ngOnInit(): void {
    this.loadFoods();
  }

  loadFoods(): void {
    this.foodService.getAllForAdmin().subscribe(foods => {
      this.foods = foods;
    });
  }

  // deleteFood(foodId: string): void {
  //   this.foodService.deleteFood(foodId).subscribe(() => {
  //     this.foods = this.foods.filter(food => food.id !== foodId);
  //   });
  // }

  editFood(foodId: string): void {
    this.router.navigate(['/edit-food', foodId]);
  }
  

  hideFood(food: Food): void {
    this.foodService.hideFood(food).subscribe(() => {
      food.hidden = true;
    });
  }

  unhideFood(food: Food): void {
    this.foodService.unhideFood(food).subscribe(() => {
      food.hidden = false;
    });
  }


  toggleHidden(food: Food): void {
    if (food.hidden) {
      this.unhideFood(food);
    } else {
      this.hideFood(food);
    }
  }

  addNewFood(): void {
    this.router.navigate(['/add-food']); 
  }
  

}
