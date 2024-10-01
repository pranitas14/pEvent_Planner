package com.example.Event.Management.Controller;

import com.example.Event.Management.Entity.CustomerData;
import com.example.Event.Management.Entity.Event;
import com.example.Event.Management.Entity.RegistrationRequest;
import com.example.Event.Management.EventRepository.CustomerDataRepository;
import com.example.Event.Management.EventRepository.EventRepository;
import com.example.Event.Management.Service.EventService;
import com.example.Event.Management.Service.PdfService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private CustomerDataRepository customerDataRepository;

    @Autowired
    private EventRepository eventRepository;

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        logger.info("Created new event with ID: {}", createdEvent.getId());
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        logger.info("Retrieved all events");
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            logger.warn("Event with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Retrieved event with ID: {}", id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @Valid @RequestBody Event eventDetails) {
        Event updatedEvent = eventService.updateEvent(id, eventDetails);
        if (updatedEvent == null) {
            logger.warn("Event with ID {} not found for update", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Updated event with ID: {}", id);
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        boolean isDeleted = eventService.deleteEvent(id);
        if (!isDeleted) {
            logger.warn("Event with ID {} not found for deletion", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Deleted event with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generateEventPdf(@PathVariable Long id) {
        logger.info("Request to generate PDF for Event ID: {}", id);

        Event event = eventService.getEventById(id);
        if (event == null) {
            logger.warn("Event with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        byte[] pdfBytes = pdfService.createEventPdf(event);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=event_" + id + ".pdf");
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);

        logger.info("PDF successfully generated for Event ID: {}", id);
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


    @PostMapping("/{eventId}/register")
    public ResponseEntity<String> registerForEvent(
            @PathVariable Long eventId,
            @Valid @RequestBody RegistrationRequest registrationRequest) {

        logger.info("Registering customer: {}", registrationRequest);

        // Attempt to register for the event
        try {
            eventService.registerForEvent(eventId, registrationRequest);
            logger.info("User registered for Event ID: {}", eventId);
            return ResponseEntity.ok("Successfully registered for the event ID: " + eventId);
        } catch (EntityNotFoundException e) {
            logger.warn("Event with ID {} not found", eventId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        } catch (Exception e) {
            logger.error("Error occurred during registration: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }
    }
}