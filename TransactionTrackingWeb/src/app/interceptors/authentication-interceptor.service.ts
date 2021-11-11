import { Injectable, Inject, Optional } from '@angular/core';
import {
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpResponse,
  HttpEvent,
} from '@angular/common/http';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';

/*
   @Author:Dieudonne Takougang
   @Date: 14/10/2021
   @Description: Intercept all protected endpoints requests and add on the request header the application api key
 */
@Injectable()
export class AuthenticationInterceptorService implements HttpInterceptor {
  constructor() { }
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const apiAccesskey = environment.apiKey;
    req = req.clone({
      url: req.url,
      setHeaders: {
        Authorization: `ApiKey ${apiAccesskey}`,
      },
    });
    return next.handle(req).pipe(
      map((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
        }
        return event;
      })
    );
  }
}
