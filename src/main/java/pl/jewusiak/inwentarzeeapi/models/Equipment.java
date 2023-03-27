package pl.jewusiak.inwentarzeeapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private LocalDateTime next_calibration;

    @OneToMany(mappedBy = "equipment", cascade = {CascadeType.REMOVE})
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "equipment", cascade = {CascadeType.REMOVE})
    private List<Event> events;

    public Equipment(long id) {
        this.id = id;
    }
}
