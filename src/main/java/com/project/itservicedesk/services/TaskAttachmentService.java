package com.project.itservicedesk.services;

import com.project.itservicedesk.models.TaskAttachment;
import com.project.itservicedesk.repositories.TaskAttachmentRepository;
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
@Transactional// important when org.hibernate.HibernateException: Unable to access lob stream error//disables autocommit
@AllArgsConstructor
public class TaskAttachmentService {

    private final TaskAttachmentRepository taskAttachmentRepository;

    public TaskAttachment convertMultipartToAttachment(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        TaskAttachment taskAttachment = new TaskAttachment(filename,
                Objects.requireNonNull(file.getContentType()), file.getBytes());

        return taskAttachment;
    }

    public TaskAttachment getAttachment(Long id) {
        return taskAttachmentRepository.findById(id).orElseThrow();
    }

    public Stream<TaskAttachment> getAllAttachments() {
        return taskAttachmentRepository.findAll().stream();
    }

    public List<TaskAttachment> getAllAttachmentsForTaskId(Long id) {
        return taskAttachmentRepository.findAllByTaskId(id);
    }

    public void delete(TaskAttachment attachment) {
        taskAttachmentRepository.delete(attachment);
    }

    public TaskAttachment save(TaskAttachment attachmentFile) {
        return taskAttachmentRepository.save(attachmentFile);
    }

    public List<TaskAttachment> saveAndFlush(List<TaskAttachment> attachmentFiles) {
        return taskAttachmentRepository.saveAllAndFlush(attachmentFiles);
    }
}
