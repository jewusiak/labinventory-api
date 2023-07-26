package pl.jewusiak.inwentarzeeapi.models.auth;

import lombok.Data;

@Data
public class RegisterRequest {

    private String displayName;
    private String email;
    private String password;
    private Integer jwtexpirytime;

}
