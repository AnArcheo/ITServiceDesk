package com.project.itservicedesk.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface AttachmentStorageService {

    void initialize();
    void saveFile(MultipartFile file);
    Resource uploadFile(String filename);
    void deleteAllFiles();
    Stream<Path> uploadMultipleFiles();



}
