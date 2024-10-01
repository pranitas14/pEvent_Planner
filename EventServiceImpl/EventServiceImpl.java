package com.example.Event.Management.EventServiceImpl;

import com.example.Event.Management.Entity.CustomerData;
import com.example.Event.Management.Entity.Event;
import com.example.Event.Management.Entity.RegistrationRequest;
import com.example.Event.Management.EventRepository.CustomerDataRepository;
import com.example.Event.Management.EventRepository.EventRepository;
import com.example.Event.Management.Service.EventService;
import com.example.Event.Management.Service.PdfService;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CustomerDataRepository customerDataRepository;

    @Autowired
    private PdfService pdfService;

    @Override
    public List<Event> getAllEvents() {
        logger.info("Retrieving all events");
        return (List<Event>) eventRepository.findAll();
    }

    @Override
    public Event getEventById(Long id) {
        logger.info("Retrieving event with ID: {}", id);
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with ID: " + id));
    }

    @Override
    public Event createEvent(Event event) {
        logger.info("Creating new event: {}", event.getEventTitle());
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long id, Event eventDetails) {
        Event event = getEventById(id);
        logger.info("Updating event with ID: {}", id);
        event.setEventTitle(eventDetails.getEventTitle());
        event.setEventDetails(eventDetails.getEventDetails());
        event.setDate(eventDetails.getDate());
        event.setLocation(eventDetails.getLocation());
        event.setTime(eventDetails.getTime());
        return eventRepository.save(event);
    }

    @Override
    public boolean deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            logger.info("Deleting event with ID: {}", id);
            eventRepository.deleteById(id);
            return true;
        } else {
            logger.warn("Event with ID {} not found for deletion", id);
            return false;
        }
    }

    @Override
    public byte[] generateEventPdf(Long id) {
        Event event = getEventById(id);
        logger.info("Generating PDF for event with ID: {}", id);
        return pdfService.createEventPdf(event);
    }

    @Override
    public void registerForEvent(Long eventId, RegistrationRequest request) {
        Event event = getEventById(eventId);
        CustomerData customerData = new CustomerData(); // Create a new CustomerData object
        customerData.setEvent(event);  // Set the event for the registration
        customerData.setName(request.getName());
        customerData.setEmail(request.getEmail());
        customerData.setPhone(request.getPhone());
        customerData.setNumGuests(request.getNumGuests());

        logger.info("Registering customer for event ID: {}", eventId);
        customerDataRepository.save(customerData);
    }

    @Override
    public Optional<Event> findEventById(Long eventId) {
        logger.info("Searching for event with ID: {}", eventId);
        return eventRepository.findById(eventId);
    }

    @Override
    public void saveEvent(Event event) {
        logger.info("Saving event: {}", event.getEventTitle());
        eventRepository.save(event);
    }
}
