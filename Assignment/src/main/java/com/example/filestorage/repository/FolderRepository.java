package com.example.filestorage.repository;

import com.example.filestorage.model.Folder;
import com.example.filestorage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByUser(User user);
    Optional<Folder> findByIdAndUser(Long id, User user);
}
