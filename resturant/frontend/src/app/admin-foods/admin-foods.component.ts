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
      this.router.navigate(['/admin-foods'])
       window.location.reload();
    });
  }

  unhideFood(food: Food): void{
    this.foodService.unhideFood(food).subscribe(()=>{
      this.router.navigate(['/admin-foods'])
      window.location.reload();
    });
  }

  toggleHidden(_t13: Food) {
    if(_t13.hidden)
      this.unhideFood(_t13);
    else
        this.hideFood(_t13);
  }

  addNewFood(): void {
    this.router.navigate(['/add-food']); 
  }
  

}
