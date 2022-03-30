import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authenticated = false;
  router:Router;
  str:any;
  constructor(private http: HttpClient,router : Router) {
    this.router=router
    if(localStorage.getItem("auth")!=null){
       this.str =localStorage.getItem("auth");
       this.authenticated = JSON.parse(this.str);
      
    }
    else{
      this.authenticated = false;
      localStorage.setItem("auth", JSON.stringify(this.authenticated));
    }
  }
  expired(){
    this.authenticated=false;
    localStorage.setItem("auth", JSON.stringify(this.authenticated));
    this.router.navigate(['/login']);
    
  }
  logout(){
    this.http.get("/api/logout").subscribe(()=>{
      this.authenticated=false;
      localStorage.setItem("auth", JSON.stringify(this.authenticated));
      this.router.navigate(['/login']);
      
    });
  }
  authenticate(data:FormGroup) {
      const params = new URLSearchParams();
      const formValue = data.value; // this.form should be a FormGroup
      
      for (const key in formValue) {
          params.append(key, formValue[key]);
      }
        this.http.post('/api/login', params.toString(),{
          observe : 'response',
          headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
          .set('Access-Control-Allow-Origin','*')
          .set('Access-Control-Allow-Methods','*')
          .set('Access-Control-Allow-Headers','Origin, Content-Type, X-Auth-Token')
      }).subscribe({next : response => {
            this.authenticated=true;
            localStorage.setItem("auth", JSON.stringify(this.authenticated));
            console.log("authenticated")
            this.router.navigate(['/'])
           
        },error: (error) => {                              //Error callback
          this.authenticated=false;
          localStorage.setItem("auth", JSON.stringify(this.authenticated));
          console.log("failed")
          this.router.navigate(['/login'],{ queryParams: { err: true } })
        }});

    }
    register(data:FormGroup){
      const params = new URLSearchParams();
      const formValue = data.value; // this.form should be a FormGroup
      
      for (const key in formValue) {
          params.append(key, formValue[key]);
      }
      this.http.post('/api/register',params.toString(),{
          observe : 'response',
          headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
          .set('Access-Control-Allow-Origin','*')
          .set('Access-Control-Allow-Methods','*')
          .set('Access-Control-Allow-Headers','Origin, Content-Type, X-Auth-Token')
      }).subscribe({next : response => {
        this.authenticated=true;
        localStorage.setItem("auth", JSON.stringify(this.authenticated));
        console.log("registered")
        this.router.navigate(['/login'])
      
    },error: (error) => {                              //Error callback
      this.authenticated=false;
      localStorage.setItem("auth", JSON.stringify(this.authenticated));
      console.log("failed")
      this.router.navigate(['/register'],{ queryParams: { err: true } })
    }});
    }

}
