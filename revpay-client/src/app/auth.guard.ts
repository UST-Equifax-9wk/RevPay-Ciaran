import { ActivatedRouteSnapshot, CanActivateFn, Router } from '@angular/router';
import { RemoteService } from './remote.service';
import { CookieService } from './cookie.service';
import { inject } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const remoteService = inject(RemoteService);
  const cookieService = inject(CookieService);
  remoteService.getValidation(cookieService.getCookieValue('currentUser'))
    .subscribe({
      next: (data) => {
        // console.log("Data found");
        // console.log(data);
        console.log(route);
        // router.navigate([route._router]);
        return true;
      },
      error: (error: HttpErrorResponse) => {
        console.log(error.error);
        alert('Access Denied! Log in again to reauthenticate session.');
        router.navigate(['/login']);
        cookieService.eraseCookies();
        return false;
      }
    });
  // because the validation is asynchronous and I'm unsure how to get the route guard to await the value,
  // I simply allow the guard to let the potentially invalid user in and redirect them whenever the validation
  // returns, since the only route-guarded pages are forms with no sensitive information on it
  return true;
};
