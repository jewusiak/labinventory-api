package pl.jewusiak.inwentarzeeapi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class FileStorageServiceTest {

    private static final String TEST_FILE_NAME = "test_file.txt";

    FileStorageService fileStorageService;


    @BeforeEach
    void setUp() {
        fileStorageService = new FileStorageService();
    }

    @Test
    void shouldSaveFile() {
        // given
        MultipartFile file = new MockMultipartFile(TEST_FILE_NAME, TEST_FILE_NAME.getBytes());

        // when
        when(UUID.randomUUID()).thenCallRealMethod();
        UUID uuid = fileStorageService.saveFile(file);

        // then
        assertThat(uuid).isNotNull();
        assertThat(Files.exists(Path.of(fileStorageService.getRootPath() + "/" + uuid))).isTrue();
    }


    @Test
    public void shouldThrowExceptionWhenSavingFileWithSameName() throws Exception {
        // given
        MultipartFile file1 = new MockMultipartFile(TEST_FILE_NAME, TEST_FILE_NAME.getBytes());
        UUID u1 = fileStorageService.saveFile(file1);
        MultipartFile file2 = new MockMultipartFile(TEST_FILE_NAME, TEST_FILE_NAME.getBytes());

        // when
        mockStatic(UUID.class);
        when(UUID.randomUUID()).thenReturn(u1);

        // then
        assertThatThrownBy(() -> fileStorageService.saveFile(file2)).isInstanceOf(RuntimeException.class).hasMessageContaining("File already exists!");
    }

    @Test
    public void shouldGetFile() throws Exception {
        // given
        UUID uuid = UUID.randomUUID();
        Path filePath = Path.of(fileStorageService.getRootPath() + "/" + uuid);
        Files.write(filePath, TEST_FILE_NAME.getBytes());

        // when
        Resource file = fileStorageService.getFile(uuid.toString());

        // then
        assertThat(file).isNotNull();
        assertThat(file.exists()).isTrue();
        assertThat(file.isReadable()).isTrue();
    }

    @Test
    public void shouldThrowExceptionWhenFileNotFound() {
        // given
        String nonExistentFileName = "non-existent-file.txt";

        // when & then
        assertThatThrownBy(() -> fileStorageService.getFile(nonExistentFileName)).isInstanceOf(RuntimeException.class).hasMessageContaining("File can't be read!");
    }
}
