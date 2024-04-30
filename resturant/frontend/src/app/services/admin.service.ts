import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { IUserRegisterWithoutConfirmPassword } from '../shared/interfaces/IUserRegister';
import { User } from '../shared/models/User';
import { ADMIN_REGISTE_URL, ALL_ADMINS_URL, ALL_CLIENTS_URL, DELETE_CLIENT } from '../shared/models/constatns/urls';
import { EncryptionService } from './AES.service';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient, private encryptionService : EncryptionService, private toastrService : ToastrService) { }

  getAllAdmins(): Observable<User[]> {
    return this.http.get<User[]>(ALL_ADMINS_URL);
  }
  registerAdmin(userRegiser:IUserRegisterWithoutConfirmPassword): Observable<User>{
    const encryptedPass = this.encryptionService.encrypt(userRegiser.password)
    userRegiser.password = encryptedPass
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem("token")}`,
      })
    };

    return this.http.post<User>(ADMIN_REGISTE_URL, userRegiser, httpOptions).pipe(
      tap({
        next: (user) => {
          this.toastrService.success(
            `User: ${user.name}, Registered Successfully`
            
          )
        },
        error: (errorResponse) => {
          this.toastrService.error(errorResponse.error,
            'Register Failed')
        }
      })
    )
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(ALL_CLIENTS_URL);
  }

  deleteUser(id: string): Observable<boolean> {
    return this.http.delete<boolean>(DELETE_CLIENT + id);
  }


}
