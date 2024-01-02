import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CookieService } from './cookie.service';

@Injectable({
  providedIn: 'root'
})
export class RemoteService {

  httpClient: HttpClient;
  cookieService: CookieService;
  url: string;
  httpOptions = {
    observe: 'response', 
    withCredentials: true,
    headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })};

  constructor(httpClient: HttpClient, cookieService: CookieService) {
    this.httpClient = httpClient;
    this.cookieService = cookieService;
    // will maybe need to change this in the future
    this.url = "http://localhost:8080";
  }

  registerUser(newUser: User): Observable<HttpResponse<Object>> {
    return this.httpClient.post(this.url + "/user", JSON.stringify(newUser), 
    {
      observe: 'response', 
      withCredentials: true,
      headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })});
  }

  login(loginDto: LoginDto): Observable<HttpResponse<Object>> {
    return this.httpClient.post(this.url + `/login`, JSON.stringify(loginDto),
    {
      observe: 'response', 
      withCredentials: true,
      headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })});
  }

  validateUser(username: string) : boolean {
    this.httpClient.get(this.url + `/cookie-test/${username}`, {
      observe: 'response', 
      withCredentials: true,
      headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })}).subscribe({
      next: (data) => {
        return true;
      },
      error: (error: HttpErrorResponse) => {
        console.log(error.error)
        return false;
      }
    })
    console.log("somehow exited subscribe statement");
    return false;
  }

  getUserInfo(username: string) : Observable<HttpResponse<Object>> {
    return this.httpClient.get(this.url + `/user/${username}`, {
      observe: 'response', 
      withCredentials: true,
      headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })});
  }

  getUserTransHistory(username: string) : Observable<HttpResponse<Object>> {
    return this.httpClient.get(this.url + `/transaction/${username}`, {
      observe: 'response', 
      withCredentials: true,
      headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })});
  }

  registerCard(newCard: Card) : Observable<HttpResponse<Object>> {
    return this.httpClient.post(this.url + `/user/${this.cookieService.getCookieValue("currentUser")}/card`, JSON.stringify(newCard), {
      observe: 'response', 
      withCredentials: true,
      headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })});
  }

  makeTransaction(newTrans: TransDto) : Observable<HttpResponse<Object>> {
    return this.httpClient.post(this.url + `/transaction/${this.cookieService.getCookieValue("currentUser")}`, 
    JSON.stringify(newTrans), {
      observe: 'response', 
      withCredentials: true,
      headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })});
  }

}

export interface User {
  accountType: string;
  username: string;
  password: string;
  email: string;
  phoneNumber: string;
}

// want to be able to log in with either username or email
export interface LoginDto {
  identifier: string;
  password: string;
}

export interface Card {
  cardType: string;
  cardNumber: string;
  expireDate: string;
  securityCode: string;
}

export interface TransDto {
  identifier: string;
  balance: number;
}