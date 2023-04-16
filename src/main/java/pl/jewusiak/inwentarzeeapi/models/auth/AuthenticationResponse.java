package pl.jewusiak.inwentarzeeapi.models.auth;

import lombok.*;
import pl.jewusiak.inwentarzeeapi.models.dtos.UserDto;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private UserDto user;
}
