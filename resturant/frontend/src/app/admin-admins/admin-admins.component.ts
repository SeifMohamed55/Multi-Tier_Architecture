import { Component, OnInit } from '@angular/core';
import { User } from '../shared/models/User';
import { AdminService } from '../services/admin.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-admins',
  templateUrl: './admin-admins.component.html',
  styleUrl: './admin-admins.component.css'
})
export class AdminAdminsComponent  implements OnInit {

  admins: User[] = [];
  //newAdmin: User = { id: '', name: '', email: ''};

  constructor(private adminService: AdminService, private router: Router) { }

  ngOnInit(): void {
    this.getAllAdmins();
  }

  getAllAdmins(): void {
    this.adminService.getAllAdmins().subscribe(admins => {
      this.admins = admins;
    });
  }

  addNewAdmin(): void {
   
      this.router.navigate(['/add-admin']);
   
  }

 



}
