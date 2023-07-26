package pl.jewusiak.inwentarzeeapi.models.auth;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String password;
    private Integer jwtexpirytime;

}
