package com.project.itservicedesk.services;

import com.project.itservicedesk.models.BugAttachment;
import com.project.itservicedesk.repositories.BugAttachmentRepository;
import com.project.itservicedesk.repositories.BugRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class BugAttachmentService {

    private final BugAttachmentRepository bugAttachmentRepository;

    public BugAttachment saveAttachment(MultipartFile file) throws IOException{
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        BugAttachment bugAttachment = new BugAttachment(filename,
                Objects.requireNonNull(file.getContentType()), file.getBytes());

        return bugAttachmentRepository.save(bugAttachment);
    }

    public BugAttachment getAttachment(Long id){
        return bugAttachmentRepository.findById(id).orElseThrow();
    }

    public Stream<BugAttachment> getAllAttachments(){
        return bugAttachmentRepository.findAll().stream();
    }
}
