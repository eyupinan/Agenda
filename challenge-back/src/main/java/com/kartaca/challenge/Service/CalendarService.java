/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kartaca.challenge.Service;

import com.kartaca.challenge.Model.Event;
import com.kartaca.challenge.Model.User;
import com.kartaca.challenge.dto.EventDto;
import com.kartaca.challenge.exception.NotFoundException;
import com.kartaca.challenge.repository.EventRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private AccountService userService;
    public List<Event> getEventList(Date startDate,Date endDate,String username){
        User user = userService.getUserByName(username);
        return eventRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndUserId(startDate, endDate,user.getId());
    }
    public void createEvent(EventDto dto,String username){
        User user = userService.getUserByName(username);
        Event event = new Event();
        event.setDetails(dto.getDetails());
        event.setEndDate(dto.getEndDate());
        event.setStartDate(dto.getStartDate());
        event.setHeader(dto.getHeader());
        event.setPerson(dto.getPerson());
        event.setUser(user);
        
        eventRepository.save(event);
    }
    public void updateEvent(EventDto dto,Long eventId){
        try{
            Event event = eventRepository.findById(eventId).get();
            if (dto.getDetails()!=null){
                event.setDetails(dto.getDetails());
            }
            if (dto.getStartDate()!=null){
                event.setStartDate(dto.getStartDate());
            }
            if(dto.getEndDate()!=null){
                event.setEndDate(dto.getEndDate());
            }
            if(dto.getHeader()!=null){
                event.setHeader(dto.getHeader());
            }
            if(dto.getPerson()!=null){
                event.setPerson(dto.getPerson());
            }
            
        eventRepository.save(event);
        }
        catch(Exception e){
            System.out.println("exception while updating event:"+e.getMessage());
            throw new NotFoundException();
        }
        
        
    }
    public void deleteEvent(String username,Long id){
        try{
            Event event = eventRepository.findById(id).get();
            if (event.getUser().getUsername().equals(username)){
                eventRepository.delete(event);
            }
        }
        catch(Exception e){
            
        }
        
    }
}
