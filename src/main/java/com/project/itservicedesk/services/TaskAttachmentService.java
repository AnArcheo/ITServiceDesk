package com.project.itservicedesk.services;

import com.project.itservicedesk.models.BugAttachment;
import com.project.itservicedesk.models.TaskAttachment;
import com.project.itservicedesk.repositories.TaskAttachmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class TaskAttachmentService {

    private final TaskAttachmentRepository taskAttachmentRepository;

    public TaskAttachment saveAttachment(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        TaskAttachment bugAttachment = new TaskAttachment(filename,
                Objects.requireNonNull(file.getContentType()), file.getBytes());

        return taskAttachmentRepository.save(bugAttachment);
    }

    public TaskAttachment getAttachment(Long id){
        return taskAttachmentRepository.findById(id).orElseThrow();
    }

    public Stream<TaskAttachment> getAllAttachments(){
        return taskAttachmentRepository.findAll().stream();
    }

    public List<TaskAttachment> getAllAttachmentsForTaskId(Long id) {
        return taskAttachmentRepository.findAllByTaskId(id);
    }
}
