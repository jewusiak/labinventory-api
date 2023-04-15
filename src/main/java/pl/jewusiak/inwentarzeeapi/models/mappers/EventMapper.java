package pl.jewusiak.inwentarzeeapi.models.mappers;

import org.mapstruct.Mapper;
import pl.jewusiak.inwentarzeeapi.models.Event;
import pl.jewusiak.inwentarzeeapi.models.dtos.EventDto;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDto mapToDto(Event entity);

    Event mapToEntity(EventDto dto);

    Collection<EventDto> mapAllToDto(Collection<Event> entities);

    Collection<Event> mapAllToEntities(Collection<EventDto> dtos);
}
