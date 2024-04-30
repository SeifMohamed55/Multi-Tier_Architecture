import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { SearchComponent } from './search/search.component';
import { FoodPageComponent } from './food-page/food-page.component';
import { TagsComponent } from './tags/tags.component';
import { CartPageComponent } from './cart-page/cart-page.component';
import { TitleComponent } from './title/title.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { LoginPageComponent } from './login-page/login-page.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { InputContainerComponent } from './input-container/input-container.component';
import { InputValidationComponent } from './input-validation/input-validation.component';
import { TextInputComponent } from './text-input/text-input.component';
import { DefaultButtonComponent } from './default-button/default-button.component';
import { RegisterPageComponent } from './register-page/register-page.component';
import { LoadingComponent } from './loading/loading.component';
import { LoadingInterceptor } from './shared/interceptors/loading.interceptor';
import { CheckoutPageComponent } from './checkout-page/checkout-page.component';
import { OrderItemsListComponent } from './order-items-list/order-items-list.component';
import { MapComponent } from './map/map.component';
import { AuthInterceptor } from './shared/interceptors/auth.interceptor';
import { PaymentPageComponent } from './payment-page/payment-page.component';
import { PaypalButtonComponent } from './paypal-button/paypal-button.component';
import { OrderTrackPageComponent } from './order-track-page/order-track-page.component';
import { ProfileComponent } from './profile/profile.component';
import { PasswordService } from './services/password.service';
import { OrdersComponent } from './orders/orders.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { AdminUsersComponent } from './admin-users/admin-users.component';
import { AdminFoodsComponent } from './admin-foods/admin-foods.component';
import { AdminAdminsComponent } from './admin-admins/admin-admins.component';
import { EditFoodComponent } from './edit-food/edit-food.component';
import { AddFoodComponent } from './add-food/add-food.component';
import { AddAdminComponent } from './add-admin/add-admin.component';




  




@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    SearchComponent,
    FoodPageComponent,
    TagsComponent,
    CartPageComponent,
    TitleComponent,
    NotFoundComponent,
    LoginPageComponent,
    InputContainerComponent,
    InputValidationComponent,
    TextInputComponent,
    DefaultButtonComponent,
    RegisterPageComponent,
    LoadingComponent,
    CheckoutPageComponent,
    OrderItemsListComponent,
    MapComponent,
    PaymentPageComponent,
    PaypalButtonComponent,
    OrderTrackPageComponent,
    ProfileComponent,
    OrdersComponent,
    AdminPageComponent,
    AdminUsersComponent,
    AdminFoodsComponent,
    AdminAdminsComponent,
    EditFoodComponent,
    AddFoodComponent,
    AddAdminComponent,
   

   
    
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,

    ReactiveFormsModule,
    ToastrModule.forRoot({
      timeOut:3000,
      positionClass:'toast-bottom-right',
      newestOnTop:false
    })
  ],
  providers: [
    {provide:HTTP_INTERCEPTORS, useClass:AuthInterceptor, multi: true },
    {provide:HTTP_INTERCEPTORS, useClass:LoadingInterceptor, multi: true },
    PasswordService
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
