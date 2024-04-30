import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';
import { User } from '../shared/models/User';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { IUpdatingUser } from '../shared/interfaces/IUpdatingUser';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profileForm!: FormGroup;
  passwordForm!: FormGroup;
  user!: User;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private toastrService: ToastrService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.user = this.userService.currentUser;
    this.profileForm = this.formBuilder.group({
      name: [this.user.name, Validators.required],
      email: [this.user.email, [Validators.required, Validators.email]],
      address: [this.user.address, Validators.required],
    });
    this.passwordForm = this.formBuilder.group({
      oldPassword: ['', Validators.required],
      newPassword: ['', Validators.required],
      confirmNewPassword: ['', Validators.required]
    });
  }

  updateProfile() {
    if (this.profileForm.valid) {
      const fullName = this.profileForm.get('name')!.value
      const nameParts = fullName.split(' ');
      const updatedUser: IUpdatingUser = {
        firstName: nameParts[0],
        lastName: nameParts[1],
        email: this.profileForm.get('email')!.value,
        address: this.profileForm.get('address')!.value
      };

      this.userService.updateUser(updatedUser).subscribe((user)=>{
        this.user = user
      });
    }
  }

  changePassword() {
    const oldPassword = this.passwordForm.get('oldPassword')!.value;
    const newPassword = this.passwordForm.get('newPassword')!.value;
    const confirmNewPassword = this.passwordForm.get('confirmNewPassword')!.value;

    if (newPassword === confirmNewPassword) {
      this.userService.changePassword(
        oldPassword,
        newPassword
      ).subscribe(
        (resp) => {
          this.passwordForm.reset();
          if(resp)
            this.toastrService.success('Password changed successfully', 'Change Password Success');
        },
        (error) => {
          this.toastrService.error('Failed to change password', 'Change Password Failed');
        }
      );
    } else {
      this.toastrService.error('New passwords do not match', 'Change Password Failed');
    }
  }
}
