package pl.jewusiak.inwentarzeeapi.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import pl.jewusiak.inwentarzeeapi.exceptions.NotFoundException;
import pl.jewusiak.inwentarzeeapi.models.Attachment;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest(properties = "spring.profiles.active=devinmem")
class AttachmentServiceTest {

    @Autowired
    private AttachmentService attachmentService;

    @Test
    void throwIfDeleteNonexistentAttachment() {
        assertThatThrownBy(() -> attachmentService.deleteAttachmentById(UUID.randomUUID())).isInstanceOf(NotFoundException.class);
    }

    @Test
    void createAttachment() {
        MockMultipartFile testFile = new MockMultipartFile("Test file", "Test file".getBytes());
        Attachment newAttachment = attachmentService.createAttachment(testFile, "test");

        assertThat(attachmentService.getAttachmentById(newAttachment.getId())).hasFieldOrPropertyWithValue("label", "test").hasFieldOrPropertyWithValue("id", newAttachment.getId());
    }

    @Test
    void deleteAttachmentSuccessful() {
        MockMultipartFile testFile = new MockMultipartFile("Test file", "Test file".getBytes());
        Attachment newAttachment = attachmentService.createAttachment(testFile, "test");

        attachmentService.deleteAttachmentById(newAttachment.getId());
    }

    @Test
    void getAttachmentAsResource() throws IOException {
        MockMultipartFile testFile = new MockMultipartFile("Test file", "Test file".getBytes());
        Attachment newAttachment = attachmentService.createAttachment(testFile, "test");

        assertThat(attachmentService.getAttachmentAsResource(newAttachment.getId()).getContentAsByteArray()).isEqualTo(testFile.getResource().getContentAsByteArray());
    }

}