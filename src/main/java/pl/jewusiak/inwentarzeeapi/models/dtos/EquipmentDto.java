package pl.jewusiak.inwentarzeeapi.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
@Data
public class EquipmentDto {
    private Long id;
    private String name;
    private String location;
    private String description;
    private LocalDateTime nextCalibration;
    private Collection<AttachmentDto> attachments;
    private Collection<EventDto> events;
}
