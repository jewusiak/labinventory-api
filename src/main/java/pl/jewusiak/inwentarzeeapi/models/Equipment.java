package pl.jewusiak.inwentarzeeapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Indexed
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericField
    private Long id;

    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID qrCodeUuid;

    @FullTextField
    private String name;

    @FullTextField
    private String description;

    @FullTextField
    private String location;

    private LocalDateTime nextCalibration;

    @OneToMany(mappedBy = "equipment", cascade = {CascadeType.REMOVE})
    private Collection<Attachment> attachments;

    @OneToMany(mappedBy = "equipment", cascade = {CascadeType.REMOVE})
    @OrderBy("dateCreated desc")
    private Collection<Event> events;

}
