import { Injectable } from '@angular/core';
import { Food } from '../shared/models/food';
import { Tag } from '../shared/models/tag';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ADD_FOOD, ALL_FOOD, FOODS_BY_ID_URL, FOODS_BY_SEARCH_URL, FOODS_BY_TAG_URL, FOODS_TAGS_URL, FOODS_URL, HIDE_FOOD, UNHIDE_FOOD, UPDATE_FOOD_URL } from '../shared/models/constatns/urls';
import { ThisReceiver } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class FoodService {

  constructor(private http:HttpClient) { }

  getAll(): Observable<Food[]>{
    return this.http.get<Food[]>(FOODS_URL);
  }

  getAllForAdmin(): Observable<Food[]>{
    return this.http.get<Food[]>(ALL_FOOD);
  }


  getAllFoodBySearchTerm(searchTerm: string){
    return this.http.get<Food[]>(FOODS_BY_SEARCH_URL + searchTerm);
  }

  getFoodById(foodId: string): Observable<Food> {
    return this.http.get<Food>(FOODS_BY_ID_URL + foodId)
  }

  getAllTags():Observable<Tag[]>{
    return this.http.get<Tag[]>(FOODS_TAGS_URL)
  }


  getAllFoodByTag(tag: string):Observable<Food[]> {
    return tag === "All" ?
        this.getAll() :
        this.http.get<Food[]>(FOODS_BY_TAG_URL + tag);
}

updateFood(updatedFood: Food): Observable<Food> {
  const httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem("token")}`
    })
  };
  return this.http.put<Food>(UPDATE_FOOD_URL, updatedFood, httpOptions);
}

hideFood(food: Food): Observable<boolean> {
  return this.http.post<boolean>(HIDE_FOOD, food);
}

unhideFood(food: Food): Observable<boolean> {
  return this.http.post<boolean>(UNHIDE_FOOD, food);
}

// deleteFood(foodId: string): Observable<void> {
//   return this.http.delete<void>(`${FOODS_URL}/${foodId}`);
// }

addNewFood(newFood: Food): Observable<Food> {
  return this.http.post<Food>(ADD_FOOD, newFood);
}



}
