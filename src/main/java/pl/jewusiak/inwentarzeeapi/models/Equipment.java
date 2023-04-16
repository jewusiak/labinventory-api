package pl.jewusiak.inwentarzeeapi.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID qrCodeUuid;
    private String name;
    private String description;
    private String location;
    private LocalDateTime next_calibration;

    @OneToMany(mappedBy = "equipment", cascade = {CascadeType.REMOVE})
    private Collection<Attachment> attachments;

    @OneToMany(mappedBy = "equipment", cascade = {CascadeType.REMOVE})
    private Collection<Event> events;

}
