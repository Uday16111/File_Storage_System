package com.example.filestorage.service;

import com.example.filestorage.model.FolderEntity;
import com.example.filestorage.model.User;
import com.example.filestorage.repository.FolderRepository;
import com.example.filestorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private UserRepository userRepository;

    public FolderEntity createFolder(String folderName, String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        FolderEntity folderEntity = new FolderEntity();
        folderEntity.setName(folderName);
        folderEntity.setUser(user);

        return folderRepository.save(folderEntity);
    }

    public List<FolderEntity> getFoldersByUser(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return folderRepository.findByUser(user);
    }
}
