package pl.jewusiak.inwentarzeeapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Attachment {
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
    private Equipment equipment;

}
