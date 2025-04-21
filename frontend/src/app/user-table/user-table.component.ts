import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-user-table',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './user-table.component.html',
  styleUrls: ['./user-table.component.css']
})
export class UserTableComponent implements OnInit {
  users: any[] = [];
  filteredUsers: any[] = [];
  filter = '';
  editUserId: number | null = null;
  editedUser: any = {};

  @Input() isAdmin: boolean = false;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetchUsers();
    console.log(this.isAdmin);
  }

  fetchUsers() {
    this.http.get<any[]>('http://localhost:8080/api/users').subscribe({
      next: data => {
        this.users = data;
        this.filteredUsers = data;
        console.log(data);
      },
      error: err => console.error('Failed to fetch users', err)
    });
  }

  filterUsers() {
    const keyword = this.filter.toLowerCase();
    this.filteredUsers = this.users.filter(user =>
      user.name.toLowerCase().includes(keyword) ||
      user.email.toLowerCase().includes(keyword) ||
      user.role.toLowerCase().includes(keyword)
    );
  }

  startEdit(user: any) {
    this.editUserId = user.id;
    this.editedUser = { ...user };
  }

  cancelEdit() {
    this.editUserId = null;
    this.editedUser = {};
  }

  saveEdit() {
    this.http.put(`http://localhost:8080/api/users/${this.editedUser.id}`, this.editedUser).subscribe({
      next: () => {
        this.fetchUsers();
        this.cancelEdit();
      },
      error: err => console.error('Update failed', err)
    });
  }

  deleteUser(id: number) {
    if (confirm('Are you sure you want to delete this user?')) {
      this.http.delete(`http://localhost:8080/api/users/${id}`, { responseType: 'text' }).subscribe({
        next: () => this.fetchUsers(),
        error: err => console.error('Delete failed', err)
      });
    }
  }
}
