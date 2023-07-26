package pl.jewusiak.inwentarzeeapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateCreated;
    @ManyToOne
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdBy;
    private String comment;
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private EventType eventType;
    @ManyToOne
    @JoinColumn(name = "equipment_id")
    @JsonIgnore
    private Equipment equipment;

    @OneToMany(mappedBy = "event", cascade = {CascadeType.REMOVE})
    private Collection<Attachment> attachments;

}