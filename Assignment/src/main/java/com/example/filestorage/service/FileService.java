package com.example.filestorage.service;

import com.example.filestorage.model.FileEntity;
import com.example.filestorage.model.User;
import com.example.filestorage.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserService userService;

    private final Path fileStorageLocation;

    @Autowired
    public FileService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getStorageLocation())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public FileEntity storeFile(MultipartFile file, String username) throws IOException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        String fileName = file.getOriginalFilename();
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(fileName);
        fileEntity.setPath(targetLocation.toString());
        fileEntity.setSize(file.getSize());
        fileEntity.setUploadTime(new Date());
        fileEntity.setUser(user);

        return fileRepository.save(fileEntity);
    }

    public List<FileEntity> getFilesByUser(User user) {
        return fileRepository.findByUser(user);
    }

    public Resource loadFileAsResource(Long fileId) {
        try {
            Optional<FileEntity> fileEntityOptional = fileRepository.findById(fileId);
            if (fileEntityOptional.isPresent()) {
                FileEntity fileEntity = fileEntityOptional.get();
                Path filePath = Paths.get(fileEntity.getPath()).normalize();
                Resource resource = new UrlResource(filePath.toUri());
                if (resource.exists()) {
                    return resource;
                } else {
                    throw new RuntimeException("File not found " + fileEntity.getName());
                }
            } else {
                throw new RuntimeException("FileEntity not found with id " + fileId);
            }
        } catch (IOException ex) {
            throw new RuntimeException("File not found", ex);
        }
    }

    public void deleteFile(Long fileId) {
        Optional<FileEntity> fileEntityOptional = fileRepository.findById(fileId);
        if (fileEntityOptional.isPresent()) {
            FileEntity fileEntity = fileEntityOptional.get();
            fileRepository.delete(fileEntity);
            try {
                Files.deleteIfExists(Paths.get(fileEntity.getPath()));
            } catch (IOException ex) {
                throw new RuntimeException("Failed to delete file", ex);
            }
        } else {
            throw new RuntimeException("FileEntity not found with id " + fileId);
        }
    }
}
