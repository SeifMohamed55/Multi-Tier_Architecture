import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { IUserRegister } from '../shared/interfaces/IUserRegister';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit {

  registerForm!:FormGroup;
  isSubmitted = false;

  returnUrl = '';
  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      confirmPassword: ['', Validators.required],
      address: ['', [Validators.required]]
    },
    {
      validator: this.customPasswordMatchValidator // Add custom validator here
    });

    this.returnUrl= this.activatedRoute.snapshot.queryParams.returnUrl;
  }

  get fc() {
    return this.registerForm.controls;
  }

  submit(){
    this.isSubmitted = true;
    if(this.registerForm.invalid) return;

    const fv= this.registerForm.controls;
    const user :IUserRegister = {
      firstName: fv.firstName.value,
      lastName : fv.lastName.value ,
      email: fv.email.value,
      password: fv.password.value,
      confirmPassword: fv.confirmPassword.value,
      address: fv.address.value
    };

    this.userService.register(user).subscribe(_ => {
      this.router.navigateByUrl(this.returnUrl);
    })
  }

  customPasswordMatchValidator(control: FormGroup): { [key: string]: any } | null {
    const password = control.get('password')!.value;
    const confirmPassword = control.get('confirmPassword')!.value;
  
    // Check if empty fields (handled by required validator)
    if (password === '' || confirmPassword === '') {
      return null;
    }
  
    // Compare password and confirmPassword
    if (password !== confirmPassword) {
      return { 'passwordsDontMatch': true }; // Use a specific error key
    }
  
    return null; // Pass validation
  }


}