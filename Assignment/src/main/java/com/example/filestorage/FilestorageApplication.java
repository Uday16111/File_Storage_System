package com.example.filestorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.example.filestorage.util.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class FilestorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilestorageApplication.class, args);
    }
}
