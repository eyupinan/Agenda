import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../Auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AccessGuard implements CanActivate {
  authService:AuthService;
  router:Router;
  constructor(authService:AuthService,router : Router){
    this.authService=authService;
    this.router=router
  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.authService.authenticated==true){
      return true;
    }
    else{
       this.router.navigate(['/login']);
       return false;
    }
    
  }
  
}
