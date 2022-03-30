import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { EventDTO, ScheduleService } from '../scheduler/schedule.service';
import { DatePipe, Time } from '@angular/common';

@Component({
  selector: 'app-new-pop',
  templateUrl: './new.component.html',
  styleUrls: ['./new.component.css']
})
export class NewComponent implements OnInit {
  @Input() newPop:boolean;
  @Output() newPopChange = new EventEmitter<boolean>();
  @Output() refreshTrigger = new EventEmitter<any>();
  date:Date;
  startDate:Date;//farklı yerlere bind işleminde karışıklığa sebep olduğu için birden fazla kopya var
  endDate:Date;
  startDateTime:any;
  endDateTime:any;
  isUpdate:boolean=false;
  httpServ:ScheduleService;
  pipe = new DatePipe('en-US');
  eventDto:EventDTO = new EventDTO();

  constructor(httpServ:ScheduleService) { 
    this.httpServ=httpServ;
  }

  ngOnInit(): void {
  }
  setDate(date:Date){
    this.eventDto=new EventDTO()
    this.date=new Date(date);
    this.startDate= new Date(date)
    this.endDate=new Date(date)
    this.startDateTime=this.date.getHours()+":"+this.date.getMinutes();
    this.endDateTime=this.date.getHours()+":"+this.date.getMinutes();
    this.isUpdate=false;
  }
  setEvent(event:EventDTO){
    this.eventDto=event;
    if (event.startDate!=null){
      this.date=new Date(<string>event.startDate);
      this.startDate=new Date(<string>event.startDate);
      this.startDateTime=this.startDate.getHours()+":"+this.startDate.getMinutes();
    }
    if (event.endDate!=null){
      this.endDate= new Date(<string>event.endDate);
      this.endDateTime=this.endDate.getHours()+":"+this.endDate.getMinutes();
    }
    
    
    this.isUpdate=true;
  }
  createNew(){
    let startDate = new Date(this.date)
    let endDate = new Date(this.date)
    startDate.setHours(this.startDateTime.split(":")[0])
    startDate.setMinutes(this.startDateTime.split(":")[1])
    endDate.setHours(this.endDateTime.split(":")[0])
    endDate.setMinutes(this.endDateTime.split(":")[1])
    this.eventDto.header=(document.getElementById("header") as HTMLInputElement).value
    let startDatestr = this.pipe.transform(startDate,"yyyy-MM-dd HH:mm:ss")
    let endDateStr= this.pipe.transform(endDate,"yyyy-MM-dd HH:mm:ss")
    this.eventDto.startDate=startDatestr;
    this.eventDto.endDate=endDateStr;
    this.eventDto.details=(document.getElementById("description") as HTMLInputElement).value
    this.eventDto.person="agit"
    if (this.isUpdate==false){
      this.httpServ.createEvent(this.eventDto,()=>{this.refresh()})
    }
    else{
      this.httpServ.updateEvent(this.eventDto,()=>{this.refresh()})
    }
    
  }
  deleteEvent(){
    if(this.isUpdate==true){// eğer update durumunda değilse zaten ortada bir id olmayacaktır
      this.httpServ.deleteEvent(this.eventDto.id,()=>this.refresh())
    }
    
  }
  refresh(){
    this.refreshTrigger.emit(null);
        this.onClose()
  }
  onClose(){
    this.newPop=false;
    this.newPopChange.emit(this.newPop)
    this.isUpdate=false;
  }

}
