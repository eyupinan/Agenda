import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './Auth/login.component';
import { RegisterComponent } from './Auth/register.component';
import { ScheduleComponent } from './scheduler/schedule.component';

const routes: Routes = [
  {path:"" , component:ScheduleComponent},
  {path:"login" , component:LoginComponent},
  {path:"register" , component:RegisterComponent},];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
