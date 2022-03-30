import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './Auth/login.component';
import { RegisterComponent } from './Auth/register.component';
import { AccessGuard } from './guard/access.guard';
import { ScheduleComponent } from './scheduler/schedule.component';

const routes: Routes = [
  {path:"" , component:ScheduleComponent,canActivate: [AccessGuard]},
  {path:"login" , component:LoginComponent},
  {path:"register" , component:RegisterComponent},];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
