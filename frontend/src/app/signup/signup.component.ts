import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
// import { AuthService } from '../services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

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
  errorMessage = '';

  // constructor(private authService: AuthService, private router: Router) { }

  async onSignup(): Promise<void> {
    // try {
    //   await this.authService.signup(this.fullName, this.email, this.password);
    //   this.router.navigate(['/login']); // ✅ după înregistrare, du-l la login
    // } catch (err: any) {
    //   this.errorMessage = err?.error || 'Signup failed'; // ✅ gestionează eroarea
    // }
  }
}
