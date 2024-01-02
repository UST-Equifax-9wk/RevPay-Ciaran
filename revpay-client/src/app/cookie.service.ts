import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CookieService {

  constructor() { 

  }

  getCookieValue(key: string): string {
    const cookies = document.cookie.split(`; `).map(pair => pair.split('='));
    for (let i = 0; i < cookies.length; i++) {
      if (cookies[i][0] === key) {
        return cookies[i][1];
      }
    }
    return "";
  }

  eraseCookies(): void {
    const keys = ["currentUser", "value"];
    keys.forEach((key) => {
      document.cookie = key + "=''; expires=" + new Date(Date.now()).toUTCString();
    })
  }

}
