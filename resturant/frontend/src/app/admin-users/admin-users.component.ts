import { Component, OnInit } from '@angular/core';
import { User } from '../shared/models/User';
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit {
  users: User[] = [];

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.adminService.getAllUsers().subscribe(users => {
      this.users = users;
    });
  }

  deleteUser(id: string): void {
    if (confirm('Are you sure you want to delete this user?')) {
      this.adminService.deleteUser(id).subscribe((resp) => {
        console.log(resp)
        this.users = this.users.filter(user => user.id !== id);
      });
    }
  }
}
