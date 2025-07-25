package ru.job4j.cinema.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.service.file.FileService;

import java.io.IOException;
import java.util.Optional;

class FileControllerTest {
    private FileService fileService;
    private FileController fileController;
    private MultipartFile testFile;

    @BeforeEach
    void initServices() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
        testFile = new MockMultipartFile("testFile.img", new byte[] {1, 2, 3});
    }

    /**
     * Test case: Request existing file by ID and verify response status is OK.
     */
    @Test
    void whenRequestFileByIdThenGetOkStatusCode() throws IOException {
        var fileDto = new FileDto(testFile.getOriginalFilename(), testFile.getBytes());
        var fileOptional = Optional.of(fileDto);
        when(fileService.getFileById(1)).thenReturn(fileOptional);

        var entity = fileController.getById(1);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test case: Request non-existing file by ID and verify response status is NOT_FOUND.
     */
    @Test
    void whenRequestNonExistedFileByIdThenGetNotFoundStatusCode() {
        var entity = fileController.getById(1);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}