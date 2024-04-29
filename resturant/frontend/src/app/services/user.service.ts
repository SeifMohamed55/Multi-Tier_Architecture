import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { User } from '../shared/models/User';
import { IUserLogin  } from '../shared/interfaces/IUserLogin';
import { IUserRegisterWithoutConfirmPassword } from '../shared/interfaces/IUserRegister';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { USER_LOGIN_URL, USER_REGISTER_URL , USER_UPDATE_URL, USER_CHANGE_PASSWORD_URL, USER_LOGOUT_URL} from '../shared/models/constatns/urls';
import { ToastrService } from 'ngx-toastr';
import { EncryptionService } from './AES.service'
import { CartService } from './cart.service';


const USER_KEY = 'User';
@Injectable({
  providedIn: 'root'
})
export class UserService {
  public userSubject = new BehaviorSubject<User>(this.getUserFromLocalStorage());
  public userObservable: Observable<User>;
  

  constructor(private http: HttpClient, private toastrService:ToastrService, private encryptionService : EncryptionService, private cartService: CartService) {
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
            `Welcome to Foodmine ${user.name}!`,
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
        // Handle successful logout
        localStorage.removeItem(USER_KEY);
        this.cartService.clearCart();
        window.location.reload();
        this.toastrService.success('User Logged out Successfully');
    },
      error: (errorResponse) => {
        localStorage.removeItem(USER_KEY);
        this.cartService.clearCart();
        window.location.reload();
        this.toastrService.error(errorResponse.error, 'Logout Failed');
        // Handle specific errors (e.g., network errors)
      }
  });
  }
  


  updateUser(user: User): Observable<User> {
    return this.http.put<User>(USER_UPDATE_URL, user).pipe(
      tap(updatedUser => {
        this.setUserToLocalStorage(updatedUser);
        this.userSubject.next(updatedUser);
      })
    );
  }

  changePassword(oldPassword: string, newPassword: string): Observable<void> {
    return this.http.put<void>(USER_CHANGE_PASSWORD_URL, { oldPassword, newPassword });
  }

 

  private setUserToLocalStorage(user: User) {
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  private getUserFromLocalStorage(): User {
    const userJson = localStorage.getItem(USER_KEY);
    if (userJson) return JSON.parse(userJson) as User;
    return new User();
  }
}
