import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-add-user',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent {
  newUser = {
    name: '',
    email: '',
    password: '',
    role: 'student'
  };

  successMessage = '';
  errorMessage = '';

  constructor(private http: HttpClient) { }

  addUser() {
    if (!this.newUser.name || !this.newUser.email || !this.newUser.password) {
      this.errorMessage = 'Please fill in all fields';
      return;
    }

    this.http.post<any>('http://localhost:8080/api/users', this.newUser).subscribe({
      next: () => {
        this.successMessage = 'User added successfully!';
        this.errorMessage = '';
        this.newUser = { name: '', email: '', password: '', role: 'student' };
        window.location.reload();
      },
      error: err => {
        console.error('Add failed', err);
        this.successMessage = '';
        this.errorMessage = 'Failed to add user.';
      }
    });


  }
}
