package pl.jewusiak.inwentarzeeapi.models.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDto {
    private Long id;
    private String displayName;
    private String email;
    private String role;
    private boolean isAccountEnabled;
}
