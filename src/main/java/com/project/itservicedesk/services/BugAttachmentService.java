package com.project.itservicedesk.services;

import com.project.itservicedesk.models.BugAttachment;
import com.project.itservicedesk.repositories.BugAttachmentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Transactional
@AllArgsConstructor
public class BugAttachmentService {

    private final BugAttachmentRepository bugAttachmentRepository;


    public BugAttachment convertMultipartToAttachment(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        BugAttachment bugAttachment = new BugAttachment(filename,
                Objects.requireNonNull(file.getContentType()), file.getBytes());

        return bugAttachment;
    }

    public BugAttachment getAttachment(Long id) {
        return bugAttachmentRepository.findById(id).orElseThrow();
    }

    public Stream<BugAttachment> getAllAttachments() {
        return bugAttachmentRepository.findAll().stream();
    }

    public List<BugAttachment> getAllAttachmentsForBugId(Long id) {
        return bugAttachmentRepository.findAllByBugId(id);
    }

    public void delete(BugAttachment attachment) {
        bugAttachmentRepository.delete(attachment);
    }

    public BugAttachment save(BugAttachment attachmentFile) {
        return bugAttachmentRepository.save(attachmentFile);
    }

    public List<BugAttachment> saveAndFlush(List<BugAttachment> attachmentFiles) {
        return bugAttachmentRepository.saveAllAndFlush(attachmentFiles);
    }
}
