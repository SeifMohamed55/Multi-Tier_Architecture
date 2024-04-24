import { Component, OnInit } from '@angular/core';
import { Food } from '../shared/models/food';
import { FoodService } from '../services/food.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{

  foods:Food[] = [];
  constructor(private foodService: FoodService, activatedRoute:ActivatedRoute){
    let foodsObservable: Observable<Food[]>;
    activatedRoute.params.subscribe((params) =>{
      if(params.searchTerm)
        foodsObservable = this.foodService.getAllFoodBySearchTerm(params.searchTerm);
      
      else if(params.tag)
        foodsObservable = this.foodService.getAllFoodByTag(params.tag);
    
      else
        foodsObservable = foodService.getAll();

        foodsObservable.subscribe((serverFood) => {
          this.foods = serverFood;
        }) 
    })
    
  }

  ngOnInit(): void {
      
  }

}
