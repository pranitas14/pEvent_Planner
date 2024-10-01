package com.example.Event.Management.Service;

import com.example.Event.Management.Entity.Event;
import com.example.Event.Management.Entity.RegistrationRequest;

import java.util.List;
import java.util.Optional;

public interface EventService {
    
    List<Event> getAllEvents();

    Event getEventById(Long id);

    Event createEvent(Event event);

    Event updateEvent(Long id, Event eventDetails);

    boolean deleteEvent(Long id);

    byte[] generateEventPdf(Long id);

    void registerForEvent(Long eventId, RegistrationRequest request);

    Optional<Event> findEventById(Long eventId);

    void saveEvent(Event event);
}
