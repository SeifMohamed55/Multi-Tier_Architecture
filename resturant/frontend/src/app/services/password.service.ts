import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { BASE_URL } from '../shared/models/constatns/urls';

@Injectable({
  providedIn: 'root'
})
export class PasswordService {

  constructor(private http: HttpClient) { }

  changePassword(oldPassword: string, newPassword: string): Observable<any> {
    return this.http.post(`${BASE_URL}/api/user/changePassword`, { oldPassword, newPassword });
  }
}
