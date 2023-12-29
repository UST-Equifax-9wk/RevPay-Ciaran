import { Routes } from '@angular/router';
import { RegisterFormComponent } from './register-form/register-form.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { HomePageComponent } from './home-page/home-page.component';

export const routes: Routes = [
    { path: "register", component: RegisterFormComponent },
    { path: "login", component: LoginFormComponent },
    { path: "home", component: HomePageComponent},
];
