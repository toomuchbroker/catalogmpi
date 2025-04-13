import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
// import { AuthService } from '../services/auth.service';

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

  // constructor(private auth: AuthService, private router: Router) {
  //   const navigation = this.router.getCurrentNavigation();
  //   this.registeredMessage = !!navigation?.extras?.state?.['registered'];
  // }

  async onLogin() {
    // try {
    //   const user = await this.auth.login(this.email, this.password);
    //   localStorage.setItem('user', JSON.stringify(user));
    //   this.router.navigate(['/encrypt']);
    // } catch (err) {
    //   this.error = 'Email sau parolă incorecte.';
    //   setTimeout(() => this.error = '', 5000);
    // }
  }
}
