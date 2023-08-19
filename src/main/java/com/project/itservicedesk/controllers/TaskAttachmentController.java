package com.project.itservicedesk.controllers;

import com.project.itservicedesk.exception.ResourceNotFoundException;
import com.project.itservicedesk.models.Task;
import com.project.itservicedesk.models.TaskAttachment;
import com.project.itservicedesk.services.TaskAttachmentService;
import com.project.itservicedesk.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskAttachmentController {

    private final TaskService taskService;
    private final TaskAttachmentService taskAttachmentService;

    @GetMapping("/tasks/{id}/files")
    public String showTaskAttachments(@PathVariable("id") Long id, Model model){
        Task task = taskService.findTaskById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task With Id = " + id + " Not Found."));

        List<TaskAttachment> attachments = taskAttachmentService.getAllAttachmentsForTaskId(id);

        model.addAttribute("task", task);
        model.addAttribute("attachments", attachments);
        model.addAttribute("message", "Attachments Not Found!!!");

        return "showAllTaskAttachments";
    }
    //open Attachment upload form
    @GetMapping("/tasks/{id}/addFiles")
    public String addAttachment(@PathVariable("id") Long id, Model model){
        Task task = taskService.findTaskById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task With Id = " + id + " Not Found."));

        model.addAttribute("task", task);
        model.addAttribute("attachments", new TaskAttachment());
        return "uploadTaskAttachmentForm";
    }

    //submit upload attachment form
    @PostMapping("/tasks/{id}/files/upload")
    public String uploadAttachments(@PathVariable("id") Long id, Model model,
                                    @RequestParam("files") MultipartFile[] files){

        //create list of info about failed and successful uploads
        List<String> uploadMessages = new ArrayList<>();
        List<TaskAttachment> allAttachmentsForTaskId = new ArrayList<>();

        Task taskToUpdate = taskService.findTaskById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task With Id = " + id + " Not Found."));

       //get list of multipart files and covert to attachment, add to attachments list
        Arrays.stream(files).forEach(file -> {
            try {
                if (!file.isEmpty() || !file.getContentType().equals("application/octet-stream")){ // prevent of uploading application/octet-stream MIME type is used for unknown binary files
                    TaskAttachment fileToAdd = taskAttachmentService.convertMultipartToAttachment(file);
                    allAttachmentsForTaskId.add(fileToAdd);
                    taskToUpdate.getTaskAttachments().add(fileToAdd);
                }

            }catch (Exception e){
                e.getMessage();
            }
        });

       taskAttachmentService.saveAndFlush(allAttachmentsForTaskId);

        model.addAttribute("allAttachments", allAttachmentsForTaskId);
        model.addAttribute("messages", uploadMessages);
        return "redirect:/tasks/{id}/files";
    }

    //delete attachment
    @GetMapping("/tasks/{id}/files/{attachmentId}")
    public String deleteFile(@PathVariable("id") Long id, @PathVariable("attachmentId") Long attachmentId) {

        taskAttachmentService.delete(taskAttachmentService.getAttachment(attachmentId));
        return "redirect:/tasks/{id}/files";
    }

    //download attachment
    @GetMapping("/tasks/{id}/files/download/{attachmentId}")
    public ResponseEntity<byte[]> getFile(@PathVariable("id") Long id, @PathVariable("attachmentId") Long attachmentId) {
        TaskAttachment file = taskAttachmentService.getAttachment(attachmentId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getAttachments());
    }
}




