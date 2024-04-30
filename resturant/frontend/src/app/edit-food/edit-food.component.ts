import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Food } from '../shared/models/food';
import { FoodService } from '../services/food.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-edit-food',
  templateUrl: './edit-food.component.html',
  styleUrls: ['./edit-food.component.css']
})
export class EditFoodComponent implements OnInit {

  food!: Food;

  constructor(private route: ActivatedRoute, private foodService: FoodService, private toastr: ToastrService, private router: Router) { }

  ngOnInit(): void {
    const foodId = this.route.snapshot.paramMap.get('id');
    if (foodId) {
      this.foodService.getFoodById(foodId).subscribe(food => {
        this.food = food;
      });
    } else {
      console.error('Food ID is null.');
    }
  }

  updateFood(): void {
    this.foodService.updateFood(this.food).subscribe(
      () => {
        this.toastr.success('Food item updated successfully.', 'Success');
        this.router.navigate(['/foodsOnAdmin']);
      },
      (error) => {
        this.toastr.error('Failed to update food item.', 'Error');
        console.error('Failed to update food item:', error);
      }
    );
  }
}

