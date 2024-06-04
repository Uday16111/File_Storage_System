package com.example.filestorage.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String path;
    private Long size;
    private Date uploadTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}
