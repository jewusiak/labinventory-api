package pl.jewusiak.inwentarzeeapi.models.mappers;

import org.mapstruct.Mapper;
import pl.jewusiak.inwentarzeeapi.models.User;
import pl.jewusiak.inwentarzeeapi.models.dtos.UserDto;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto mapToDto(User entity);

    User mapToEntity(UserDto dto);

    Collection<UserDto> mapAllToDto(Collection<User> entities);

    Collection<User> mapAllToEntities(Collection<UserDto> dtos);
}
