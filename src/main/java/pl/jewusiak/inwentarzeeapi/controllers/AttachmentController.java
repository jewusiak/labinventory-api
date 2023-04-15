package pl.jewusiak.inwentarzeeapi.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jewusiak.inwentarzeeapi.models.Attachment;
import pl.jewusiak.inwentarzeeapi.models.dtos.AttachmentDto;
import pl.jewusiak.inwentarzeeapi.models.mappers.AttachmentMapper;
import pl.jewusiak.inwentarzeeapi.services.AttachmentService;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final AttachmentMapper attachmentMapper;

    public AttachmentController(AttachmentService attachmentService, AttachmentMapper attachmentMapper) {
        this.attachmentService = attachmentService;
        this.attachmentMapper = attachmentMapper;
    }

    @GetMapping("")
    public Collection<AttachmentDto> getAllAttachments() {
        return attachmentMapper.mapAllToDto(attachmentService.getAllAttachments());
    }

    @PostMapping("")
    public AttachmentDto createAttachment(@RequestParam("file") MultipartFile file, @RequestParam(value = "label", required = false, defaultValue = "") String label) {
        return attachmentMapper.mapToDto(attachmentService.createAttachment(file, label));
    }

    @GetMapping("/getbyid/{id}")
    public AttachmentDto getAttachmentById(@PathVariable UUID id) {
        return attachmentMapper.mapToDto(attachmentService.getAttachmentById(id));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadAttachmentById(@PathVariable UUID id) {
        Attachment attachment = attachmentService.getAttachmentById(id);
        Resource resource = attachmentService.getAttachmentAsResource(id);
        var responseEntity = ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + attachment.getOriginalFileName() + "\"");
        responseEntity.contentType(switch (attachment.getViewableAttachmentType()) {
            case PDF -> MediaType.APPLICATION_PDF;
            case PNG -> MediaType.IMAGE_PNG;
            case JPEG -> MediaType.IMAGE_JPEG;
            default -> MediaType.APPLICATION_OCTET_STREAM;

        });
        return responseEntity.body(resource);
    }

    @DeleteMapping("/{id}")
    public void deleteAttachmentById(@PathVariable UUID id) {
        attachmentService.deleteAttachmentById(id);
    }

    @PostMapping("/{attachment_id}/assign_equipment/{equipment_id}")
    public AttachmentDto assignEquipment(@PathVariable UUID attachment_id, @PathVariable Long equipment_id) {
        return attachmentMapper.mapToDto(attachmentService.assignEquipment(attachment_id, equipment_id));
    }

    @PostMapping("/{attachment_id}/assign_event/{event_id}")
    public AttachmentDto assignEvent(@PathVariable UUID attachment_id, @PathVariable Long event_id) {
        return attachmentMapper.mapToDto(attachmentService.assignEvent(attachment_id, event_id));
    }
}
