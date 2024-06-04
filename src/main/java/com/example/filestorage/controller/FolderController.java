package com.example.filestorage.controller;

import com.example.filestorage.model.FolderEntity;
import com.example.filestorage.service.FolderService;
import com.example.filestorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/folders")
public class FolderController {

    @Autowired
    private FolderService folderService;

    @Autowired
    private UserService userService;

    @GetMapping("/create")
    public String showCreateFolderForm() {
        return "createFolder";
    }

    @PostMapping("/create")
    public String createFolder(@RequestParam("folderName") String folderName, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            folderService.createFolder(folderName, username);
            model.addAttribute("message", "Folder created successfully: " + folderName);
        } catch (Exception e) {
            model.addAttribute("message", "Failed to create folder: " + e.getMessage());
        }
        return "createFolder";
    }

    @GetMapping("/list")
    public String listFolders(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<FolderEntity> folders = folderService.getFoldersByUser(username);
        model.addAttribute("folders", folders);
        return "folderList";
    }
}
