import {  AfterViewInit, Component,  ElementRef,  OnInit, TemplateRef, ViewChild } from '@angular/core';
import { AuthService } from '../Auth/auth.service';
import { NewComponent } from '../Popups/new.component';
import { EventDTO, ScheduleService, User } from './schedule.service';
class DateInfo{
  dayName:String;
  dayNumber:Number;
  date:Date;
}

class EventGraph{
  event:EventDTO;
  height:String;
  top:String;
  left:String;
  color:String;
}
@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent implements OnInit,AfterViewInit {
  auth:AuthService;
  scheduleService :ScheduleService;
  user:User;
  activeDate :Date = new Date();
  activeDays:Array<DateInfo>;
  events:Array<EventGraph>=[];
  sunday:Date;
  newPop:boolean=false;
  blockSize:number=72;
  @ViewChild(NewComponent) newComponent:NewComponent;
  @ViewChild('eventContainer', { read: TemplateRef }) eventContainer:TemplateRef<any>;
  @ViewChild('row', { read: ElementRef }) row:ElementRef;
 

  days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
  scrollDown:number=0;
  constructor(auth:AuthService,scheduleService :ScheduleService) {
    this.activeDaysCreater(this.activeDate)
    this.auth=auth;
    this.scheduleService =scheduleService;
   }

   getRandomColor() {
      var letters = '0123456789ABCDEF';
      var color = '#';
      for (var i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
      }
      return color;
    }
   calculateEventHeight(event:EventDTO):String{
    var diff = Math.abs(new Date(<string>event.endDate).getTime()- new Date(<string>event.startDate).getTime());
    var minutes = Math.floor((diff/1000)/60);
    return ((minutes/60) * this.blockSize) +"px";
   }
   calculateEventPosition(event:EventDTO):[String,String]{
    let columnSizePercent=12.5
    let startHours=new Date(<string>event.startDate).getHours()
    let startMinutes= new Date(<string>event.startDate).getMinutes();
    let x = columnSizePercent*(new Date(<string>event.startDate).getDay()+1)+"%";
    let y = this.blockSize*(startHours+(startMinutes/60))+"px";
    return [x,y]
   }
  ngOnInit(): void {
  }
  ngAfterViewInit() {
    this.blockSize=this.row.nativeElement.offsetHeight*2;// her bir yarım saatlik bloğun yükseklik değerine göre eventler boyutlandırılıyor.
    this.refreshEvents(null);
  }
  refreshEvents(event){
    let dt = new Date(this.sunday)
    this.events=[]

    dt.setDate(this.sunday.getDate() +7)
    this.scheduleService.getEvents(this.sunday,dt).subscribe({next:(body)=>{
      let arr = body as unknown as EventDTO[];
      for(let i=0;i<arr.length;i++){
        let graph = new EventGraph();
        graph.event=arr[i];
        graph.height=this.calculateEventHeight(arr[i]);
        let position=this.calculateEventPosition(arr[i])
        graph.left=position[0];
        graph.top=position[1];
        graph.color=this.getRandomColor()
        this.events.push(graph)
      }
      
    },error:(error)=>{
      this.auth.expired()
    }});
  }
  activeDaysCreater(activeDate:Date){
    activeDate= new Date(activeDate);

    this.activeDays=[]
    activeDate.setDate(activeDate.getDate() -activeDate.getDay());
    this.sunday=new Date(activeDate);
    this.sunday.setHours(this.sunday.getHours()-this.sunday.getHours());
    this.sunday.setMinutes(this.sunday.getMinutes()-this.sunday.getMinutes());
    this.sunday.setSeconds(this.sunday.getSeconds()-this.sunday.getSeconds());
    for(let i=0;i<7;i++){
      var dayName = this.days[activeDate.getDay()];
      let obj={dayName:dayName,dayNumber:activeDate.getDate(),date:activeDate}
      this.activeDays.push(obj)
      activeDate.setDate(activeDate.getDate() +1);
    }
    
  }
  onDateChange(){
    this.activeDaysCreater(this.activeDate);
    this.refreshEvents(null)
    
  }
  counter(i: Array<EventGraph>) {
    if(i==undefined){
      return Array(0);
    }
    return new Array(i.length);
}
  timeString(event:EventDTO){
    let str ="";
    str+=new Date(<string>event.startDate).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})+  " - " 
        + new Date(<string>event.endDate).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});
    return str
  }
  range(i){
    return new Array(i)
  }
  setDateForButton(dayDiff:number,minDiff){
    let d = new Date(this.sunday)
    d.setDate(d.getDate()+dayDiff)
    d.setMinutes(d.getMinutes()+minDiff)
    return d
  }
  createEvent(el){
    let date = new Date(el.target.getAttribute("data-date"))
    this.newComponent.setDate(date)
    this.newPop=true;
  }
  updateEvent(event){
    let elementId = event.target.getAttribute("id")
    let eventId =elementId.split("_")[1]
    for(let i =0;i<this.events.length;i++){
      if(this.events[i].event.id==eventId){
        this.newComponent.setEvent(this.events[i].event)
        this.newPop=true;
        break;
      }
    }
  }
  perform_logout(){
    this.auth.logout()
  }
  changeWeek(day:number){
    this.activeDate=new Date(this.activeDate)
    this.activeDate.setDate(this.activeDate.getDate()+day);
    this.onDateChange()
  }


}
