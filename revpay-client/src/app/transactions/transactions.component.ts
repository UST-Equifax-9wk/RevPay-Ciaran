import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RemoteService, TransDto } from '../remote.service';
import { TransplantedType } from '@angular/compiler';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './transactions.component.html',
  styleUrl: './transactions.component.css'
})
export class TransactionsComponent {

  router: Router;
  remoteService: RemoteService;
  identifier: string;
  amount: number;

  constructor(router: Router, remoteService: RemoteService) {
    this.router = router;
    this.remoteService = remoteService;
    this.identifier = "";
    this.amount = 0;
  }

  // doesn't check for max 2 decimal places since nothing works
  isValid(): boolean {
    return (!!+this.amount) && +this.amount > 0;
  }

  // always truncates values to 2 decimal places
  adjustDecimals(num: number): number {
    const multiplier = 100,
          adjustedNum = num * multiplier,
          truncatedNum = Math[adjustedNum < 0 ? 'ceil' : 'floor'](adjustedNum);

    return truncatedNum / multiplier;
  };

  submitSend() {
    let newTrans: TransDto = {
      identifier: this.identifier,
      // apparently somewhere I flip the amount because I don't have to do it here
      balance: this.adjustDecimals(this.amount),
    } 
    this.remoteService.makeTransaction(newTrans)
      .subscribe({
        next: (data) => {
          alert("Money sent!");
          console.log(data);
          this.router.navigate(['/home']);
        },
        error: (error: HttpErrorResponse) => {
          alert("Couldn't send money");
          console.log(error.error);
        }
      })
  }

  submitRequest() {
    console.log("Should send a payment request, not implemented");
  }
}
