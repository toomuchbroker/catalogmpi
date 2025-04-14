import { Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';
import { LoginComponent } from './login/login.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },

    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'signup', loadComponent: () => import('./signup/signup.component').then(m => m.SignupComponent) },
    { path: 'admin-panel', loadComponent: () => import('./admin-pannel/admin-pannel.component').then(m => m.AdminPannelComponent) },
    //{ path: 'user-panel', loadComponent: () => import('./user-panel/user-panel.component').then(m => m.UserPanelComponent)}

    { path: '**', redirectTo: 'login' }
];
