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
    localStorage.clear();
  }

  onLogin() {
    const body = {
      email: this.email,
      password: this.password
    };

    this.http.post<any>('http://localhost:8080/api/users/login', body).subscribe({
      next: (response) => {
        localStorage.setItem('user', JSON.stringify(response));

        if (response.role === 'admin') {
          this.router.navigate(['/admin-panel']);
        } else {
          console.log("fml");
          this.router.navigate(['/user-panel']);
        }
      },
      error: () => {
        this.error = 'Email sau parolă incorecte.';
        setTimeout(() => (this.error = ''), 15000);
      }
    });
  }
}
