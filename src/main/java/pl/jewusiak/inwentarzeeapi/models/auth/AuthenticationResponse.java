package pl.jewusiak.inwentarzeeapi.models.auth;

import lombok.Builder;
import lombok.Data;
import pl.jewusiak.inwentarzeeapi.models.dtos.UserDto;


@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private UserDto user;
}
