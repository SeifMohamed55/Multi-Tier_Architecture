import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Food } from '../shared/models/food';
import { FoodService } from '../services/food.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-food',
  templateUrl: './add-food.component.html',
  styleUrls: ['./add-food.component.css']
})
export class AddFoodComponent {

  newFoodForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private foodService: FoodService, private router: Router, private toastr: ToastrService) {
    this.newFoodForm = this.formBuilder.group({
      name: ['', Validators.required],
      price: [0, Validators.required],
      imageUrl: ['assets/not_found.jpg'],
      origins: [[]],
      cookTime: ['', Validators.required],
      tags: ['', Validators.required],
      hidden: [false]
    });
  }

  addNewFood(): void {
    if (this.newFoodForm.invalid) {
      this.toastr.error('Please fill in all required fields.', 'Error');
      return;
    }

     this.newFoodForm.value.tags = [this.newFoodForm.value.tags];
     let newFood: Food = this.newFoodForm.value
    this.foodService.addNewFood(newFood).subscribe(
      () => {
        this.toastr.success('Food item added successfully.', 'Success');
        this.router.navigate(['/foodsOnAdmin']); // Navigate back to the list of foods
      },
      (error) => {
        this.toastr.error('Failed to add food item.', 'Error');
        console.error('Failed to add food item:', error);
      }
    );
  }

}
