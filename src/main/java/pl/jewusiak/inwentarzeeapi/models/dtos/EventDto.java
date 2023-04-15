package pl.jewusiak.inwentarzeeapi.models.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
@Getter
@Setter
public class EventDto {
    private Long id;
    private LocalDateTime dateCreated;
    private UserDto createdBy;
    private String comment;
    private String eventType;
    private Long equipmentId;
    private Collection<AttachmentDto> attachments;

}
