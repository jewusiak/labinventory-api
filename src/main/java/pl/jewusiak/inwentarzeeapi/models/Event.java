package pl.jewusiak.inwentarzeeapi.models;

import java.time.LocalDateTime;

public class Event {
    private long id;
    private LocalDateTime dateCreated;
    private User createdBy;
    private String comment;
    private EventType eventType;
}
