package pl.jewusiak.inwentarzeeapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Attachment {

    private static final String baseUrl = "http://127.0.0.1:8080";

    @Id
    private UUID id;

    private String label;

    private String originalFileName;

    @Transient
    private String downloadUrl;

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

    public String getDownloadUrl() {
        return baseUrl + "/attachment/download/" + getId();
    }
}
