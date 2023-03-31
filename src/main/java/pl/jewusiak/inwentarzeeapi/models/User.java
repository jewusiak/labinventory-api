package pl.jewusiak.inwentarzeeapi.models;

import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.jewusiak.inwentarzeeapi.models.dtos.UserDto;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String displayName;

    @Column(unique = true)
    private String email;

    private String password;

    public enum UserRole {USER, ADMIN};

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, columnDefinition = "smallint default 0")
    private UserRole role;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isAccountEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isAccountEnabled;
    }

    public UserDto toDto(){
        return UserDto.builder().id(id).displayName(displayName).email(email).role(role.name()).isAccountEnabled(isAccountEnabled).build();
    }
}
