package com.example.filestorage.repository;

import com.example.filestorage.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByUserId(Long userId);
    List<FileEntity> findByFolderId(Long folderId);
}
