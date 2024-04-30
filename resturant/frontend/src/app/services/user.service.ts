import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { User } from '../shared/models/User';
import { IUserLogin  } from '../shared/interfaces/IUserLogin';
import { IUserRegisterWithoutConfirmPassword } from '../shared/interfaces/IUserRegister';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { USER_LOGIN_URL, USER_REGISTER_URL , USER_UPDATE_URL, USER_CHANGE_PASSWORD_URL, USER_LOGOUT_URL, ADMIN_REGISTE_URL} from '../shared/models/constatns/urls';
import { ToastrService } from 'ngx-toastr';
import { EncryptionService } from './AES.service'
import { CartService } from './cart.service';
import { Router } from '@angular/router';
import { IUpdatingUser } from '../shared/interfaces/IUpdatingUser';




const USER_KEY = 'User';
@Injectable({
  providedIn: 'root'
})
export class UserService {
  public userSubject = new BehaviorSubject<User>(this.getUserFromLocalStorage());
  public userObservable: Observable<User>;
  

  constructor(private http: HttpClient,
     private toastrService:ToastrService, private encryptionService : EncryptionService, private cartService: CartService, private router: Router) {
    this.userObservable = this.userSubject.asObservable();
  }

  public get currentUser(): User {
    return this.userSubject.value;
  }

  login(userLogin:IUserLogin):Observable<User>{
    const encryptedPass = this.encryptionService.encrypt(userLogin.password)
    userLogin.password = encryptedPass
    return this.http.post<User>(USER_LOGIN_URL, userLogin).pipe(
      tap({
        next: (user) =>{        
          this.setUserToLocalStorage(user);
          localStorage.setItem("token", user.token);
          this.userSubject.next(user);
          this.toastrService.success(
            `Welcome to RAZ ${user.name}!`,
            'Login Successful'
          )
        },
        error: (errorResponse) => {
          this.toastrService.error(errorResponse.error, 'Login Failed');
        }
      })
    );
  }




  register(userRegiser:IUserRegisterWithoutConfirmPassword): Observable<User>{
    const encryptedPass = this.encryptionService.encrypt(userRegiser.password)
    userRegiser.password = encryptedPass
    return this.http.post<User>(USER_REGISTER_URL, userRegiser).pipe(
      tap({
        next: (user) => {
          this.setUserToLocalStorage(user);
          this.userSubject.next(user);
          this.toastrService.success(
            `Welcome to the Foodmine ${user.name}`,
            'Register Successful'
          )
        },
        error: (errorResponse) => {
          this.toastrService.error(errorResponse.error,
            'Register Failed')
        }
      })
    )
  }


 

 /* logout(): Observable<void> {
    return this.http.post<void>(USER_LOGOUT_URL, {}).pipe(
      tap(() => {
        this.userSubject.next(null);
        localStorage.removeItem(USER_KEY);
      })
    );
  }
  */
  


  logout() {
    this.userSubject.next(new User());
    return this.http.post(USER_LOGOUT_URL, this.getUserFromLocalStorage(),{ 
      responseType: 'text' ,
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem("token")}`
      })
  }).subscribe({
      next: () => {
        localStorage.removeItem(USER_KEY);
        this.cartService.clearCart();
        window.location.reload();
        this.toastrService.success('User Logged out Successfully');
        this.router.navigate(["/"]);

       
    },
      error: (errorResponse) => {
        localStorage.removeItem(USER_KEY);
        this.cartService.clearCart();
        window.location.reload();
        this.toastrService.error(errorResponse.error, 'Logout Failed');
      }
  });
  }
  


  updateUser(user: IUpdatingUser): Observable<User> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem("token")}`
      })
    };
    return this.http.post<User>(USER_UPDATE_URL, user, httpOptions).pipe(
      tap({
        next: (user) => {
          this.setUserToLocalStorage(user);
          localStorage.setItem("token", user.token);
          this.userSubject.next(user);
          this.toastrService.success('Profile updated successfully', 'Update Success');
        },
        error: (errorResponse) => {
          this.toastrService.error('Failed to update profile', 'Update Failed');
        }
    }
    ));
  }

  changePassword(oldPassword: string, newPassword: string): Observable<boolean> {
    oldPassword =  this.encryptionService.encrypt(oldPassword)
    newPassword =  this.encryptionService.encrypt(newPassword)
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem("token")}`
      })
    };
    return this.http.post<boolean>(USER_CHANGE_PASSWORD_URL, { oldPassword: oldPassword, newPassword: newPassword}, httpOptions);
  }

 

  private setUserToLocalStorage(user: User) {
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  private getUserFromLocalStorage(): User {
    const userJson = localStorage.getItem(USER_KEY);
    if (userJson) return JSON.parse(userJson) as User;
    return new User();
  }


  isUserAdmin(): boolean {
    const user = this.currentUser;
    let aa= false

    if(!user || !user.roles) return false;

    for (const role of user.roles) {
      if(role.authority == "ROLE_ADMIN")
        aa = true;
    }
    
    return user && aa;
}



}
  




