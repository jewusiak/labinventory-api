package pl.jewusiak.inwentarzeeapi.services;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jewusiak.inwentarzeeapi.exceptions.NotFoundException;
import pl.jewusiak.inwentarzeeapi.models.Attachment;
import pl.jewusiak.inwentarzeeapi.models.ViewableAttachmentType;
import pl.jewusiak.inwentarzeeapi.repositories.AttachmentRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Collection;
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

    public Collection<Attachment> getAllAttachments() {
        return attachmentRepository.findAll();
    }

    public Attachment getAttachmentById(UUID id) {
        return attachmentRepository.findById(id).orElseThrow(() -> new NotFoundException("attachment"));
    }

    public void deleteAttachmentById(UUID id) {
        Attachment attachment = getAttachmentById(id);
        fileStorageService.removeFile(attachment.getId().toString());

        attachmentRepository.delete(attachment);

    }

    public Attachment createAttachment(MultipartFile file, String label) {

        String originalFileName = file.getOriginalFilename();
        originalFileName = originalFileName == null ? "" : originalFileName;
        String extension = "";
        if (originalFileName.lastIndexOf('.') >= 0)
            extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
        Attachment attachment = new Attachment();
        attachment.setViewableAttachmentType(ViewableAttachmentType.classifyFile(extension));

        if (ViewableAttachmentType.isImage(attachment.getViewableAttachmentType())) {
            //resize
            try {
                BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
                int biggerDimension = Math.max(bufferedImage.getHeight(), bufferedImage.getWidth());
                double scale = 800. / biggerDimension;
                int newWidth = (int) (bufferedImage.getWidth() * scale), newHeight = (int) (bufferedImage.getHeight() * scale);

                var baos = new ByteArrayOutputStream();
                Thumbnails.of(file.getInputStream()).size(newWidth, newHeight).outputQuality(0.7f)
                        .outputFormat(extension).toOutputStream(baos);
                var bytes = baos.toByteArray();
                baos.close();

                MultipartFile finalFile = file;

                file = new MultipartFile() {
                    @Override
                    public String getName() {
                        return finalFile.getName();
                    }

                    @Override
                    public String getOriginalFilename() {
                        return finalFile.getOriginalFilename();
                    }

                    @Override
                    public String getContentType() {
                        return finalFile.getContentType();
                    }

                    @Override
                    public boolean isEmpty() {
                        return finalFile.isEmpty();
                    }

                    @Override
                    public long getSize() {
                        return bytes.length;
                    }

                    @Override
                    public byte[] getBytes() {
                        return bytes;
                    }

                    @Override
                    public InputStream getInputStream() {
                        return new ByteArrayInputStream(bytes);
                    }

                    @Override
                    public void transferTo(File dest) throws IOException, IllegalStateException {
                        Files.write(dest.toPath(), bytes);
                    }
                };
            } catch (IOException e) {
                attachment.setViewableAttachmentType(ViewableAttachmentType.NV);
                System.err.println("Error in resizing!");
                e.printStackTrace();
            }
        }

        UUID uuid = fileStorageService.saveFile(file);

        attachment.setLabel(label);
        attachment.setOriginalFileName(originalFileName);
        attachment.setId(uuid);
        return attachmentRepository.save(attachment);
    }

    public Resource getAttachmentAsResource(UUID attachmentId) {
        return fileStorageService.getFile(attachmentId.toString());
    }

    public Attachment assignEquipment(UUID attachmentId, Long equipmentId) {
        Attachment attachment = getAttachmentById(attachmentId);
        attachment.setEquipment(equipmentService.getEquipmentById(equipmentId));
        return attachmentRepository.save(attachment);
    }

    public Attachment assignEvent(UUID attachmentId, Long eventId) {
        Attachment attachment = getAttachmentById(attachmentId);
        attachment.setEvent(eventService.getEventById(eventId));
        return attachmentRepository.save(attachment);
    }
}
