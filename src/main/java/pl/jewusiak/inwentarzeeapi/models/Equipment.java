package pl.jewusiak.inwentarzeeapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID qrCodeUuid;
    private String name;
    private String description;
    private LocalDateTime next_calibration;

    @OneToMany(mappedBy = "equipment", cascade = {CascadeType.REMOVE})
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "equipment", cascade = {CascadeType.REMOVE})
    private List<Event> events;

}
