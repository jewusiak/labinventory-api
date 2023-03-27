package pl.jewusiak.inwentarzeeapi.services;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jewusiak.inwentarzeeapi.exceptions.NotFoundException;
import pl.jewusiak.inwentarzeeapi.models.Attachment;
import pl.jewusiak.inwentarzeeapi.models.ViewableAttachmentType;
import pl.jewusiak.inwentarzeeapi.repositories.AttachmentRepository;

import java.util.UUID;

@Service
public class AttachmentService {

    private final FileStorageService fileStorageService;
    private final AttachmentRepository attachmentRepository;
    private final EquipmentService equipmentService;
    private final EventService eventService;


    public AttachmentService(AttachmentRepository attachmentRepository, FileStorageService fileStorageService, EquipmentService equipmentService, EventService eventService) {
        this.attachmentRepository = attachmentRepository;
        this.fileStorageService = fileStorageService;
        this.equipmentService = equipmentService;
        this.eventService = eventService;
    }

    public Iterable<Attachment> getAllAttachments() {
        return attachmentRepository.findAll();
    }

    public Attachment getAttachmentById(UUID id) {
        return attachmentRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void deleteAttachmentById(UUID id) {
        attachmentRepository.delete(getAttachmentById(id));
    }

    public Attachment createAttachment(MultipartFile file, String label) {
        Attachment attachment = new Attachment();
        UUID uuid = fileStorageService.saveFile(file);
        String originalFileName = file.getOriginalFilename();
        originalFileName = originalFileName == null ? "" : originalFileName;
        String extension = "";
        if (originalFileName.lastIndexOf('.') >= 0)
            extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
        attachment.setViewableAttachmentType(ViewableAttachmentType.classifyFile(extension));
        attachment.setLabel(label);
        attachment.setOriginalFileName(originalFileName);
        attachment.setId(uuid);
        return attachmentRepository.save(attachment);
    }

    public Resource downloadAttachment(UUID attachmentId) {
        return fileStorageService.getFile(attachmentId.toString());
    }

    public Attachment assignEquipment(UUID attachmentId, long equipmentId) {
        Attachment attachment = getAttachmentById(attachmentId);
        attachment.setEquipment(equipmentService.getEquipmentById(equipmentId));
        return attachmentRepository.save(attachment);
    }

    public Attachment assignEvent(UUID attachmentId, long eventId) {
        Attachment attachment = getAttachmentById(attachmentId);
        attachment.setEvent(eventService.getEventById(eventId));
        return attachmentRepository.save(attachment);
    }
}
