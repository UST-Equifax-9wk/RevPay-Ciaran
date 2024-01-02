import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Card, RemoteService } from '../remote.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-cards',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './cards.component.html',
  styleUrl: './cards.component.css'
})
export class CardsComponent {
  router: Router;
  remoteService: RemoteService;
  cardType: string;
  cardNumber: string;
  expireDate: string;
  securityCode: string;

  constructor(router: Router, remoteService: RemoteService) {
    this.router = router;
    this.remoteService = remoteService;
    this.cardType = "";
    this.cardNumber = "";
    this.expireDate = "";
    this.securityCode = "";
  }

  submit() {
    console.log("Submitting...");
    let newCard: Card = {
      cardType: this.cardType,
      cardNumber: this.cardNumber,
      expireDate: this.expireDate,
      securityCode: this.securityCode,
    };
    this.remoteService.registerCard(newCard)
      .subscribe({
        next: (data) => {
          alert("Card registered!");
          console.log(data);
          this.router.navigate(['/home']);
        },
        error: (error: HttpErrorResponse) => {
          alert("Couldn't Register");
          console.log(error.error);
        }
      })
  }
}
