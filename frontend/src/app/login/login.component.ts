import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email = '';
  password = '';
  error = '';
  registeredMessage = false;

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    sessionStorage.clear();
  }

  isEmailValid(): boolean {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.email);
  }

  isFormValid(): boolean {
    return this.isEmailValid() && this.password.trim().length >= 6;
  }

  onLogin(): void {
    if (!this.isFormValid()) {
      this.error = 'Email invalid sau parolă prea scurtă.';
      setTimeout(() => (this.error = ''), 5000);
      return;
    }

    const body = { email: this.email, password: this.password };

    this.http.post<any>('http://localhost:8080/api/users/login', body).subscribe({
      next: (response) => {
        sessionStorage.setItem('user', JSON.stringify(response));
        this.router.navigate([response.role === 'admin' ? '/admin-panel' : '/user-panel']);
      },
      error: () => {
        this.error = 'Email sau parolă incorecte.';
        setTimeout(() => (this.error = ''), 15000);
      }
    });
  }
}
