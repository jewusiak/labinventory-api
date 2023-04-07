package pl.jewusiak.inwentarzeeapi.models.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RegisterRequest {

    private String displayName;
    private String email;
    private String password;
    private Integer jwtexpirytime;

}
