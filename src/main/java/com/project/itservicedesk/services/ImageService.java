package com.project.itservicedesk.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveUserProfilePhoto(Long userId, MultipartFile file);
}
