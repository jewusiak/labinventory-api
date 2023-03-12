package pl.jewusiak.inwentarzeeapi.models;

import java.time.LocalDateTime;
import java.util.List;

public class Equipment {
    private long id;
    private String name;
    private String description;
    private LocalDateTime next_calibration;

    private List<Attachment> attachments;

    private List<Event> events;
}
