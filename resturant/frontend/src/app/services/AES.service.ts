import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class EncryptionService {

  private secretKey: any = CryptoJS.enc.Hex.parse('9B0343B24E4B13B67EE3DB525AF809BF363658BC4EF7899CEA18CCE0C046510B');

  private iv: any = CryptoJS.enc.Hex.parse('06EFD400E83428CB81E7640FCF95C714');

  constructor(private http: HttpClient) {}

  encrypt(plainText: string): string {
    const ciphered = CryptoJS.AES.encrypt(CryptoJS.enc.Utf8.parse(plainText), this.secretKey, { 
      iv: this.iv
       }).toString();

    return ciphered;
  }

  decrypt(encryptedText: string): string {
    const bytes = CryptoJS.AES.decrypt(encryptedText, this.secretKey, {
      iv: this.iv
    });
    return bytes.toString(CryptoJS.enc.Utf8);
  }

  getData(): Observable<any> {
    const url = `http://localhost:8080/match?angSecKey=${this.secretKey}&angIv=${this.iv}`;
    return this.http.get<boolean>(url);
  }
}