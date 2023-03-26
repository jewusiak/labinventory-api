package pl.jewusiak.inwentarzeeapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private LocalDateTime next_calibration;

    @OneToMany(mappedBy = "equipment")
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "equipment")
    private List<Event> events;
}
