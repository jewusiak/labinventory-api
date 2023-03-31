package pl.jewusiak.inwentarzeeapi.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jewusiak.inwentarzeeapi.models.Attachment;
import pl.jewusiak.inwentarzeeapi.services.AttachmentService;

import java.util.UUID;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping("")
    public Iterable<Attachment> getAllAttachments() {
        return attachmentService.getAllAttachments();
    }

    @PostMapping("")
    public Attachment createAttachment(@RequestParam("file") MultipartFile file, @RequestParam(value = "label", required = false, defaultValue = "") String label) {
        return attachmentService.createAttachment(file, label);
    }

    @GetMapping("/getbyid/{id}")
    public Attachment getAttachmentById(@PathVariable UUID id) {
        return attachmentService.getAttachmentById(id);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadAttachmentById(@PathVariable UUID id) {
        Attachment attachment = attachmentService.getAttachmentById(id);
        Resource resource = attachmentService.downloadAttachment(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getOriginalFileName() + "\"").body(resource);
    }

    @DeleteMapping("/{id}")
    public void deleteAttachmentById(@PathVariable UUID id) {
        attachmentService.deleteAttachmentById(id);
    }

    @PostMapping("/{attachment_id}/assign_equipment/{equipment_id}")
    public Attachment assignEquipment(@PathVariable UUID attachment_id, @PathVariable long equipment_id) {
        return attachmentService.assignEquipment(attachment_id, equipment_id);
    }

    @PostMapping("/{attachment_id}/assign_event/{event_id}")
    public Attachment assignEvent(@PathVariable UUID attachment_id, @PathVariable long event_id) {
        return attachmentService.assignEvent(attachment_id, event_id);
    }
}
