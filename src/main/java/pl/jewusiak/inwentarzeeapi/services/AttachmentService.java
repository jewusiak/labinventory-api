package pl.jewusiak.inwentarzeeapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.jewusiak.inwentarzeeapi.exceptions.NotFoundException;
import pl.jewusiak.inwentarzeeapi.models.Attachment;
import pl.jewusiak.inwentarzeeapi.repositories.AttachmentRepository;

@Service
public class AttachmentService {


    private final AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public Iterable<Attachment> getAllAttachments(){
        return attachmentRepository.findAll();
    }

    public Attachment getAttachmentById(long id){
        return attachmentRepository.findById(id).orElseThrow(() -> new NotFoundException("attachment"));
    }

    public void deleteAttachmentById(long id){
        attachmentRepository.delete(getAttachmentById(id));
    }

    public Attachment createAttachment(Attachment attachment){
        attachment.setId(-1);
        return attachmentRepository.save(attachment);
    }

    public Attachment updateAttachment(String json, long id) throws JsonProcessingException {
        Attachment oldAttachment=getAttachmentById(id);
        Attachment finalAttachment=new ObjectMapper().readerForUpdating(oldAttachment).readValue(json);
        finalAttachment.setId(id);
        return attachmentRepository.save(finalAttachment);
    }
}
