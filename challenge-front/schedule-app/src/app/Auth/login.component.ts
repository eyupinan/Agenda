import {  Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  profileForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });
  httpserv:AuthService;
  router:Router;
  failed:boolean=false;
  constructor(httpserv: AuthService,router :Router,private route: ActivatedRoute) { 
    this.router=router;
    this.httpserv=httpserv;
  }

  ngOnInit(): void {
    this.route.queryParams
      .subscribe(params => {
        if (params["err"]==true || params["err"]=="true"){
          this.failed=true;
        }
      }
    );
  }
  onSubmit(){
    this.httpserv.authenticate(this.profileForm)
  }


}
