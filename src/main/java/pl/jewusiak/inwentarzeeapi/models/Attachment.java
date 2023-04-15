package pl.jewusiak.inwentarzeeapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {

    @Id
    private UUID id;

    private String label;

    private String originalFileName;

    @Enumerated(EnumType.ORDINAL)
    private ViewableAttachmentType viewableAttachmentType;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    @JsonIgnore
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonIgnore
    private Event event;

    public void setEquipment(Equipment equipment) {
        if (equipment != null) event = null;
        this.equipment = equipment;
    }

    public void setEvent(Event event) {
        if (event != null) equipment = null;
        this.event = event;
    }

}
