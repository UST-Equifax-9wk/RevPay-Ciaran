import { Routes } from '@angular/router';
import { RegisterFormComponent } from './register-form/register-form.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { HomePageComponent } from './home-page/home-page.component';
import { LandingComponent } from './landing/landing.component';
import { CardsComponent } from './cards/cards.component';
import { TransactionsComponent } from './transactions/transactions.component';

export const routes: Routes = [
    { path: "", component: LandingComponent },
    { path: "register", component: RegisterFormComponent },
    { path: "login", component: LoginFormComponent },
    { path: "home", component: HomePageComponent },
    { path: "cards", component: CardsComponent },
    { path: "transactions", component: TransactionsComponent }
];
