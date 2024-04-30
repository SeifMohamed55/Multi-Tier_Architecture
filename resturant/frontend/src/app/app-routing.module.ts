import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { FoodPageComponent } from './food-page/food-page.component';
import { CartPageComponent } from './cart-page/cart-page.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { RegisterPageComponent } from './register-page/register-page.component';
import { CheckoutPageComponent } from './checkout-page/checkout-page.component';
import { AuthGuard } from './auth/guard/auth.guard';
import { PaymentPageComponent } from './payment-page/payment-page.component';
import { OrderTrackPageComponent } from './order-track-page/order-track-page.component';
import { ProfileComponent } from './profile/profile.component';
import { OrdersComponent } from './orders/orders.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { AdminFoodsComponent } from './admin-foods/admin-foods.component';
import { AdminAdminsComponent } from './admin-admins/admin-admins.component';
import {AdminUsersComponent } from './admin-users/admin-users.component';
import { EditFoodComponent } from './edit-food/edit-food.component';
import { AddFoodComponent } from './add-food/add-food.component';
import { AddAdminComponent } from './add-admin/add-admin.component';
import { AdminAuthGuard } from './auth/guard/admin.auth.guard';




const routes: Routes = [
  {path:'', component:HomeComponent},
  {path:'search/:searchTerm',component:HomeComponent},
  {path: 'food/:id', component:FoodPageComponent},
  {path: 'tag/:tag', component: HomeComponent},
  {path: 'cart-page', component:CartPageComponent, canActivate:[AuthGuard]},
  {path: 'login', component:LoginPageComponent},
  {path: 'register', component:RegisterPageComponent},
  {path:'checkout', component: CheckoutPageComponent, canActivate:[AuthGuard]},
  {path:'payment', component: PaymentPageComponent, canActivate:[AuthGuard]},
  {path:'track/:orderId', component: OrderTrackPageComponent, canActivate:[AuthGuard]},
  {path: 'profile', component:ProfileComponent, canActivate:[AuthGuard]},
  {path: 'orders', component:OrdersComponent, canActivate:[AuthGuard]},
  {path: 'admin-page', component:AdminPageComponent , canActivate:[AdminAuthGuard]},
  {path: 'usersOnAdmin', component:AdminUsersComponent , canActivate:[AdminAuthGuard]},
  {path: 'foodsOnAdmin', component:AdminFoodsComponent , canActivate:[AdminAuthGuard]},
  {path: 'adminsOnAdmin', component:AdminAdminsComponent , canActivate:[AdminAuthGuard]},
  { path: 'edit-food/:id', component: EditFoodComponent , canActivate:[AdminAuthGuard]},
  { path: 'add-food', component: AddFoodComponent , canActivate:[AdminAuthGuard]},
  { path: 'add-admin', component: AddAdminComponent , canActivate:[AdminAuthGuard]},

 




];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
