import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RemoteService {

  httpClient: HttpClient;
  url: string;
  httpOptions = {
    observe: 'response', 
    withCredentials: true,
    headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })};

  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;

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