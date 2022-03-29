/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.kartaca.challenge.repository;

import com.kartaca.challenge.Model.Event;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndUserId(Date startDate,Date endDate,Long id);
}