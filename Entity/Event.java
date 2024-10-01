package com.example.Event.Management.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Event title is mandatory")
    @Size(max = 100, message = "Event title must not exceed 100 characters")
    private String eventTitle;

    @NotBlank(message = "Event details are mandatory")
    @Size(max = 500, message = "Event details must not exceed 500 characters")
    private String eventDetails;

    @NotNull(message = "Event date is mandatory")
    private LocalDate date;

    @NotBlank(message = "Event location is mandatory")
    @Size(max = 200, message = "Event location must not exceed 200 characters")
    private String location;

    @NotNull(message = "Event time is mandatory")
    private LocalTime time;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerData> customers = new ArrayList<>();

    // Customer Data जोडण्याची आणि काढून टाकण्याची सोपी पद्धत
    public void addCustomerData(CustomerData customerData) {
        customerData.setEvent(this);
        customers.add(customerData);
    }

    public void removeCustomerData(CustomerData customerData) {
        customerData.setEvent(null);
        customers.remove(customerData);
    }

    // Getters आणि Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public List<CustomerData> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerData> customers) {
        this.customers = customers;
    }
}
