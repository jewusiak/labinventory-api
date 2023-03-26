package pl.jewusiak.inwentarzeeapi.services;

import org.springframework.beans.factory.annotation.Value;
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
    @Value("${inwentarz-ee.baseAddress}")
    private String baseUrl;

    public AttachmentService(AttachmentRepository attachmentRepository, FileStorageService fileStorageService) {
        this.attachmentRepository = attachmentRepository;
        this.fileStorageService = fileStorageService;
    }

    public Iterable<Attachment> getAllAttachments() {
        return attachmentRepository.findAll();
    }

    public Attachment getAttachmentById(UUID id) {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new NotFoundException("attachment"));
        attachment.setDownloadUrl(baseUrl + "/attachment/download/" + attachment.getId());
        return attachment;
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

}
