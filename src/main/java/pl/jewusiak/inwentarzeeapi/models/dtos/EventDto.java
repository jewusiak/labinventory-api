package pl.jewusiak.inwentarzeeapi.models.dtos;

import lombok.AccessLevel;
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
    @Setter(AccessLevel.NONE)
    private LocalDateTime dateCreated;
    @Setter(AccessLevel.NONE)
    private UserDto createdBy;
    private String comment;
    private String eventType;
    private Collection<AttachmentDto> attachments;

}
