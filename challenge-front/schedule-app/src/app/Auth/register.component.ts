import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./login.component.css']
})
export class RegisterComponent implements OnInit {
  profileForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    phone: new FormControl(''),
    address: new FormControl(''),
    email: new FormControl(''),
  });
  failed:boolean=false;
  httpserv:AuthService;
  router:Router;
  constructor(httpserv: AuthService,router :Router,private route: ActivatedRoute) {
    this.httpserv=httpserv;
    this.router=router
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
    this.httpserv.register(this.profileForm)
  }

}
