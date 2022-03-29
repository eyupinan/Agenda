import { DatePipe } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
export class User{
  username:String;
  constructor(username:String){
    this.username=username;
  }
}
export class EventDTO{
  id:number;
  startDate:String;
  endDate:String;
  header:String;
  details:String;
  person:String;
}
@Injectable({
  providedIn: 'root'
})
export class ScheduleService {
  http:HttpClient;
  constructor(http :HttpClient) {
    this.http=http;
   }
   getUser(){

     return this.http.get<User>("/api/user",{observe:"body"});
   }
   getEvents(startDate:Date,endDate:Date){
    console.log("start:",startDate,"end:",endDate)
    let pipe = new DatePipe('en-US');
    return this.http.get<EventDTO[]>('api/event',{
      params: new HttpParams().set("startDate",pipe.transform(startDate, 'dd-MM-yyyy')).set("endDate",pipe.transform(endDate, 'dd-MM-yyyy')),
      observe: 'body'
    })
    /*.map((response: Response) => {
        return <Event[]>response.json();
    }));*/
   }
   createEvent(event:EventDTO,callback:any){
     console.log(event)
     return this.http.post('/api/event',event).subscribe({next:(res)=>{
       callback()
     },
    error : (error)=>{
      console.log(error)
    }})
   }
   updateEvent(event:EventDTO,callback:any){
    console.log(event)
    return this.http.put('/api/event/'+event.id,event).subscribe({next:(res)=>{
      callback()
    },
   error : (error)=>{
     console.log(error)
   }})
   }
   deleteEvent(id:any,callback:any=null){
    return this.http.delete('/api/event/'+id).subscribe({next:(res)=>{
      if (callback!=null){
        callback()
      }
      
    },
   error : (error)=>{
     console.log(error)
   }})
   }
   

}
