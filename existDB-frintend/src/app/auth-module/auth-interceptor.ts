import {Injectable} from '@angular/core';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpResponse,
  HttpErrorResponse,
  HTTP_INTERCEPTORS
} from '@angular/common/http';
import {TokenStorageService} from './token-storage.service';
import {tap} from 'rxjs/operators';
import {NotificationService} from '../error-notification-module/service/notification.service';
import {Router} from '@angular/router';


const TOKEN_HEADER_KEY = 'Authorization';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
      private token: TokenStorageService,
      private notificationService: NotificationService,
      private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let authReq = req;
    const token = TokenStorageService.getToken();
    if (token != null) {
      authReq = req.clone({headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token)});
    }
    // return next.handle(authReq);


    // @ts-ignore
    return next.handle(authReq).pipe(tap((event: HttpEvent) => {
      if (event instanceof HttpResponse) {
        console.log('Service Response thr Interceptor');
      }
    }, (err: any) => {
      if (err instanceof HttpErrorResponse) {
        if (err.status === 401 || err.status === 403) {
          if (err.error.message === 'Username or password is incorrect') {
            this.notificationService.Error2(err.error.message);
          } else if (err.error.message === 'Other error - Authentication failed!') {
            this.router.navigate(['/login']);
          } else {
            this.notificationService.Error2(err.error.message);
            this.token.signOut();
            this.router.navigate(['/login']);
          }
        } else if (err.status === 0) {
          // console.log('ERR_CONNECTION_REFUSED');
          this.router.navigate(['/error-page']);
          // location.href = '/error-page';
        }
        // else if (err.status === 200) {
        //   console.log(err.error-page.text);
        // }
        // if (err.status === 400) {
        //   // location.href = '/register';
        //   console.log('error-page, massage: ' + err.error-page.message);
        // }
      }
    }));
  }
}

export const httpInterceptorProvider = [
  {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
];
