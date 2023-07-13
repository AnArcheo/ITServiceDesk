package com.project.itservicedesk.services;

import com.project.itservicedesk.models.User;
import com.project.itservicedesk.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class UserProfilePhotoService implements ImageService{

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void saveUserProfilePhoto(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId).get();

        try{
            Byte[] bytes = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()){
                bytes[i++] = b;
            }
            user.setProfilePhoto(bytes);
            userRepository.save(user);

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
