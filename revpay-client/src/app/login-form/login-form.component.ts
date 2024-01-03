import { Component } from '@angular/core';
import { RemoteService, LoginDto } from '../remote.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [FormsModule, CommonModule, NavbarComponent],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css'
})
export class LoginFormComponent {
  router: Router;
  remoteService: RemoteService;
  identifier: string;
  password: string;

  constructor(router: Router, remoteService: RemoteService) {
    this.router = router;
    this.remoteService = remoteService;
    this.identifier = "";
    this.password = "";
  }

  login() {
    let loginDto: LoginDto = {
      identifier: this.identifier,
      password: this.password
    }
    this.remoteService.login(loginDto)
    .subscribe({
      next: (data) => {
        alert("Login successful!")
        console.log(data)
        this.router.navigate(["/home"]);
      },
      error: (error: HttpErrorResponse) => {
        alert(error.error)
        console.log(error.error)
      }
    })
  }
}
