import { Component } from '@angular/core';
import { CookieService } from '../cookie.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  cookieService: CookieService;

  constructor(cookieService: CookieService) {
    this.cookieService = cookieService;
  } 

}
