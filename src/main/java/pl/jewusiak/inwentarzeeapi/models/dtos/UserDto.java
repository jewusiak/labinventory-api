package pl.jewusiak.inwentarzeeapi.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Boolean isAccountEnabled;

    @JsonProperty("isAccountEnabled")
    public boolean isAccountEnabled() {
        return isAccountEnabled;
    }
}
