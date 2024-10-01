package com.example.Event.Management.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;
    private String time; 
    
    // This can be a token or some identifier for the recipient

    // Constructors
    public Notification() {}

    public Notification(String title, String message, String recipient) {
        this.title = title;
        this.message = message;
        this.time = time;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecipient() {
        return time;
    }

    public void setRecipient(String recipient) {
        this.time = recipient;
    }
}
