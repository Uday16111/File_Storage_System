package com.example.filestorage.controller;

import com.example.filestorage.model.FileEntity;
import com.example.filestorage.model.User;
import com.example.filestorage.service.FileService;
import com.example.filestorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            fileService.storeFile(file, username);
            model.addAttribute("message", "File uploaded successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
            model.addAttribute("message", "Failed to upload file: " + e.getMessage());
        }
        return "upload";
    }

    @GetMapping("/list")
    public String listFiles(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        List<FileEntity> files = fileService.getFilesByUser(user);
        model.addAttribute("files", files);
        return "fileList";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        Resource resource = fileService.loadFileAsResource(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Long fileId, Model model) {
        try {
            fileService.deleteFile(fileId);
            model.addAttribute("message", "File deleted successfully");
        } catch (Exception e) {
            model.addAttribute("message", "Failed to delete file: " + e.getMessage());
        }
        return "redirect:/api/files/list";
    }
}
