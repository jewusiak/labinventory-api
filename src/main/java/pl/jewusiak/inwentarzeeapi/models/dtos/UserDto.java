package pl.jewusiak.inwentarzeeapi.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private long id;
    private String displayName;
    private String email;
    private String role;
    private boolean isAccountEnabled;
}
