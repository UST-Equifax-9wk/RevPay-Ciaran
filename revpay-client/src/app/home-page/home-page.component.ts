import { Component } from '@angular/core';
import { RemoteService } from '../remote.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {
  router: Router;
  remoteService: RemoteService;
  accountType: string;
  username: string;
  email: string;
  phoneNumber: string;
  balance: number;
  cards: Card[];
  transactions: Transaction[];
  
  constructor(router: Router, remoteService: RemoteService) {
    this.router = router;
    this.remoteService = remoteService;
    this.accountType = "";
    this.username = "";
    this.email = "";
    this.phoneNumber = "";
    this.balance = 0;
    this.cards = [];
    this.transactions = [];
    let cookies = document.cookie;
    let currentUser = this.getCookieValue("username", cookies);
    this.remoteService.getUserInfo(currentUser)
    .subscribe({
      next: (data) => {
        let userInfo = JSON.parse(JSON.stringify(data.body ? data.body : {})); // just to deal with the type checking
        console.log(userInfo);
        this.accountType = userInfo.accountType;
        this.username = userInfo.username;
        this.email = userInfo.email;
        this.phoneNumber = userInfo.phoneNumber;
        this.balance = userInfo.balance;
        userInfo.cards.forEach((card: object) => {
          this.cards.push(JSON.parse(JSON.stringify(card)))
        });
        userInfo.payments.forEach((trans: object) => {
          let out = JSON.parse(JSON.stringify(trans));
          out.prefix = "Sent";
          out.suffix = "to " + out.username;
          this.transactions.push(out);
        });
        userInfo.receipts.forEach((trans: object) => {
          let out = JSON.parse(JSON.stringify(trans));
          out.prefix = 'Received';
          out.suffix = 'from ' + out.username;
          this.transactions.push(out);
        });
        this.transactions.sort((a, b) => a.timestamp < b.timestamp ? 1 : (a.timestamp > b.timestamp ? -1 : 0));
      },
      error: (error: HttpErrorResponse) => {
        alert("Couldn't verify login information!")
        console.log(error.error)
      }
    });
  }

  getCookieValue(key: string, cookie_list: string): string {
    const cookies = cookie_list.split(`; `).map(pair => pair.split('='));
    for (let i = 0; i < cookies.length; i++) {
      if (cookies[i][0] === key) {
        return cookies[i][1];
      }
    }
    return "";
  }

  addCardPage(): void {
    console.log("Should redirect to add new cards page");
  }
}

export interface Transaction {
  username: string;
  amount: number;
  timestamp: string;
  // need these two to properly construct HTML output
  prefix: string;
  suffix: string;
}

export interface Card {
  cardType: string;
  cardNumber: string;
  expireDate: string;
  code: string;
}

export interface UserInfo {
  accountType: string;
  username: string;
  // omitting password
  email: string;
  phoneNumber: string;
  balance: number;
  cards: Card[];
  payments: Transaction[];
  receipts: Transaction[];
}
