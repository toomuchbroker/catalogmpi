import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  fullName = '';
  email = '';
  password = '';
  role = 'student';
  errorMessage = '';
  successMessage = '';

  constructor(private http: HttpClient, private router: Router) { }

  onSignup(): void {
    if (!this.fullName.trim() || !this.email.trim() || !this.password.trim()) {
      this.errorMessage = 'Te rugăm să completezi toate câmpurile.';
      return;
    }

    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.email)) {
      this.errorMessage = 'Email-ul introdus nu este valid.';
      return;
    }

    if (this.password.length < 6) {
      this.errorMessage = 'Parola trebuie să aibă cel puțin 6 caractere.';
      return;
    }

    const user = {
      name: this.fullName,
      email: this.email,
      password: this.password,
      role: this.role
    };

    this.http.post('http://localhost:8080/api/users', user).subscribe({
      next: () => {
        this.errorMessage = '';
        this.successMessage = 'Cont creat cu succes! Redirecționare către login...';
        setTimeout(() => this.router.navigate(['/login']), 2000);
      },
      error: err => {
        this.successMessage = '';
        this.errorMessage = err.error?.message || 'Eroare la crearea contului.';
      }
    });
  }
}
