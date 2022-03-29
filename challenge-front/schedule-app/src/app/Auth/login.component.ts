import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { ScheduleService } from '../scheduler/schedule.service';
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
  scheduleService:ScheduleService
  constructor(httpserv: AuthService,router :Router,scheduleService:ScheduleService) { 
    this.router=router;
    this.httpserv=httpserv;
    this.scheduleService=scheduleService;
  }

  ngOnInit(): void {
  }
  onSubmit(){
    this.httpserv.authenticate(this.profileForm)
    this.scheduleService.getUser()
  }


}
