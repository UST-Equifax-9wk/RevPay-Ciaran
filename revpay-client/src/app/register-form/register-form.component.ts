import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RemoteService, User } from '../remote.service';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.css'
})
export class RegisterFormComponent {
  router: Router;
  remoteService: RemoteService;
  account_type: string;
  username: string;
  password: string;
  email: string;
  phone: string;

  constructor(router: Router, remoteService: RemoteService) {
    this.router = router;
    this.remoteService = remoteService;
    this.account_type = "";
    this.username = "";
    this.password = "";
    this.email = "";
    this.phone = "";
  }

  submit() {
    console.log("Submitting...");
    let newUser: User = {
      accountType: this.account_type,
      username: this.username,
      password: this.password,
      email: this.email,
      phoneNumber: this.phone,
    };
    this.remoteService.registerUser(newUser)
      .subscribe({
        next: (data) => {
          alert("User registered!")
          console.log(data)
          this.router.navigate(["/login"])

        },
        error: (error: HttpErrorResponse) => {
          alert("Couldn't Register")
          console.log(error.error)
        }
      })
  }
}
