/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kartaca.challenge.restController;

import com.kartaca.challenge.Model.Event;
import com.kartaca.challenge.Service.CalendarService;
import com.kartaca.challenge.dto.EventDto;
import com.kartaca.challenge.dto.UserPrincipal;
import com.kartaca.challenge.exception.ForbiddenException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api")
public class EventController {
    @Autowired
    private CalendarService calendarService;
    @GetMapping(value="/event", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getUser(Authentication auth,@RequestParam(name = "startDate",required = true) @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                            @RequestParam(name = "endDate",required = true) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate){
        if (auth==null){
            throw new ForbiddenException();
        }
        UserPrincipal principal = (UserPrincipal)auth.getPrincipal();

        return calendarService.getEventList(startDate, endDate, principal.getUsername());
        
    }
    @PostMapping(value="/event")
    public void createEvent(Authentication auth,@RequestBody EventDto eventDto){
        if(auth==null){
            throw new ForbiddenException();
        }
        UserPrincipal principal = (UserPrincipal)auth.getPrincipal();
        calendarService.createEvent(eventDto,principal.getUsername());
    }
    
    @PutMapping(value="/event/{id}")
    public void updateEvent(Authentication auth,@RequestBody EventDto eventDto,@PathVariable(value="id") Long id){
        if(auth==null){
            throw new ForbiddenException();
        }
        UserPrincipal principal = (UserPrincipal)auth.getPrincipal();
        calendarService.updateEvent(eventDto,id);
    }
    @DeleteMapping(value="/event/{id}")
    public void deleteEvent(Authentication auth,@PathVariable(value="id") Long id){
        if(auth==null){
            throw new ForbiddenException();
        }

        UserPrincipal principal = (UserPrincipal)auth.getPrincipal();
        calendarService.deleteEvent(principal.getUsername(),id);
    }
}
