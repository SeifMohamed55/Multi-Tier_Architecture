
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EncryptionService } from '../services/AES.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
  loginForm!:FormGroup;
  isSubmitted = false;
  returnUrl = '';
  constructor(private encServ:EncryptionService, private formBuilder: FormBuilder, private userService: UserService, private activatedRoute: ActivatedRoute, private router:Router)   { }
  
  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email:['', [Validators.required,Validators.email]],
      password:['', Validators.required]
    });

    this.returnUrl = this.activatedRoute.snapshot.queryParams.returnUrl;
  }

  get fc(){
    return this.loginForm.controls;
  }

  submit(){
    this.isSubmitted = true;
    if(this.loginForm.invalid) return;

    this.userService.login({email: this.fc.email.value,password: this.fc.password.value}).subscribe(() =>{
      this.router.navigateByUrl(this.returnUrl);
    });
  }

  getKey(){
    this.encServ.getData().subscribe(response =>{
      console.log(response)
    })
  }

}