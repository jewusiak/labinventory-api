package pl.jewusiak.inwentarzeeapi.models.auth;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthenticationRequest {
    private String email;
    private String password;
    private Integer jwtexpirytime;

}
