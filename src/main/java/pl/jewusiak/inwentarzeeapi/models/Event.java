package pl.jewusiak.inwentarzeeapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime dateCreated;
    @ManyToOne
    @JoinColumn(name = "created_by_user_id", nullable = true)
    private User createdBy;
    private String comment;
    @Enumerated(EnumType.ORDINAL)
    private EventType eventType;
    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = true)
    @JsonIgnore
    private Equipment equipment;

    @OneToMany(mappedBy = "event", cascade = {CascadeType.REMOVE})
    private List<Attachment> attachments;
}