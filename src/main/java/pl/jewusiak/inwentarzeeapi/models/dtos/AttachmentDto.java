package pl.jewusiak.inwentarzeeapi.models.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import pl.jewusiak.inwentarzeeapi.models.ViewableAttachmentType;

import java.util.UUID;

@Data
public class AttachmentDto {

    private UUID id;

    private String label;

    private String originalFileName;

    @Getter(AccessLevel.NONE)
    private String downloadUrl;

    private ViewableAttachmentType viewableAttachmentType;

    public String getDownloadUrl() {
        return "/attachment/download/" + getId();
    }

}
