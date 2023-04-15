package pl.jewusiak.inwentarzeeapi.models.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pl.jewusiak.inwentarzeeapi.models.Equipment;
import pl.jewusiak.inwentarzeeapi.models.dtos.EquipmentDto;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface EquipmentMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEquipmentFromDto(EquipmentDto dto, @MappingTarget Equipment entity);

    EquipmentDto mapToDto(Equipment entity);

    Equipment mapToEntity(EquipmentDto dto);

    Collection<EquipmentDto> mapAllToDto(Collection<Equipment> entities);

    Collection<Equipment> mapAllToEntities(Collection<EquipmentDto> dtos);

}
