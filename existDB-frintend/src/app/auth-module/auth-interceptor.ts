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


const TOKEN_HEADER_KEY = 'Authorization';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
      private token: TokenStorageService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let authReq = req;
    const token = this.token.getToken();
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
        console.log('err.status', err);
        if (err.status === 401 || err.status === 403) {
          console.log('error massage: ' + err.error.message);
          if (err.error.message === 'Username or password is incorrect') {
            console.log('Username or password is incorrect');
          } else {
            console.log(err.error.message);
            this.token.signOut();
            location.href = '/login';
          }
        } else if (err.status === 0) {
          console.log('ERR_CONNECTION_REFUSED');
          // location.href = '/error';
        } else if (err.status === 200) {
          console.log(err.error.text);
        }
        // if (err.status === 400) {
        //   // location.href = '/register';
        //   console.log('error, massage: ' + err.error.message);
        // }
      }
    }));
  }
}

export const httpInterceptorProvider = [
  {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
];
