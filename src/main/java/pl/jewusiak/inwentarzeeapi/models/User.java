package pl.jewusiak.inwentarzeeapi.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String displayName;
    private String email;
    private byte[] passwordHash;
    private byte[] passwordSalt;

    public User(long id) {
        this.id = id;
    }
}
