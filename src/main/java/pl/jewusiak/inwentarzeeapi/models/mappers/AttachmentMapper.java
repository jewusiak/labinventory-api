package pl.jewusiak.inwentarzeeapi.models.mappers;

import org.mapstruct.Mapper;
import pl.jewusiak.inwentarzeeapi.models.Attachment;
import pl.jewusiak.inwentarzeeapi.models.dtos.AttachmentDto;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    AttachmentDto mapToDto(Attachment entity);

    Attachment mapToEntity(AttachmentDto dto);

    Collection<AttachmentDto> mapAllToDto(Collection<Attachment> entities);

    Collection<Attachment> mapAllToEntities(Collection<AttachmentDto> dtos);
}
