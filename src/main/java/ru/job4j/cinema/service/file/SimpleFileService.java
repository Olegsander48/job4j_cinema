package ru.job4j.cinema.service.file;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.repository.file.FileRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class SimpleFileService implements FileService {
    private final FileRepository fileRepository;
    private final ResourceLoader resourceLoader;

    public SimpleFileService(FileRepository sql2oFileRepository, ResourceLoader resourceLoader) {
        this.fileRepository = sql2oFileRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Optional<FileDto> getFileById(int id) {
        var fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new FileDto(fileOptional.get().getName(), content));
    }

    private byte[] readFileAsBytes(String path) {
        try {
            var resource = resourceLoader.getResource("classpath:static/" + path);
            try (InputStream in = resource.getInputStream()) {
                return in.readAllBytes();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + path, e);
        }
    }
}
