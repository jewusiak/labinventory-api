package pl.jewusiak.inwentarzeeapi.models.dtos;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
@Data
public class EventDto {
    private Long id;
    @Setter(AccessLevel.NONE)
    private LocalDateTime dateCreated;
    @Setter(AccessLevel.NONE)
    private UserDto createdBy;
    private String comment;
    private String eventType;
    private Collection<AttachmentDto> attachments;

}
