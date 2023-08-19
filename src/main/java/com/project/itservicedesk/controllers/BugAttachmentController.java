package com.project.itservicedesk.controllers;

import com.project.itservicedesk.exception.ResourceNotFoundException;
import com.project.itservicedesk.models.Bug;
import com.project.itservicedesk.models.BugAttachment;
import com.project.itservicedesk.services.BugAttachmentService;
import com.project.itservicedesk.services.BugService;
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
public class BugAttachmentController {

    private final BugService bugService;
    private final BugAttachmentService bugAttachmentService;


    @GetMapping("/bugs/{id}/files")
    public String showBugAttachments(@PathVariable("id") Long id, Model model){
        Bug bug = bugService.findBugById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bug With Id = " + id + " Not Found."));

        List<BugAttachment> attachments = bugAttachmentService.getAllAttachmentsForBugId(id);

        model.addAttribute("bug", bug);
        model.addAttribute("attachments", attachments);
        model.addAttribute("message", "Attachments Not Found!!!");

        return "showAllBugAttachments";
    }
    //open Attachment upload form
    @GetMapping("/bugs/{id}/addFiles")
    public String addAttachment(@PathVariable("id") Long id, Model model){
        Bug bug = bugService.findBugById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bug With Id = " + id + " Not Found."));

        model.addAttribute("bug", bug);
        model.addAttribute("attachments", new BugAttachment());
        return "uploadBugAttachmentForm";
    }

    //submit upload attachment form
    @PostMapping("/bugs/{id}/files/upload")
    public String uploadAttachments(@PathVariable("id") Long id, Model model,
                                    @RequestParam("files") MultipartFile[] files){

        //create list of info about failed and successful uploads
        List<String> uploadMessages = new ArrayList<>();
        List<BugAttachment> allAttachmentsForBugId = new ArrayList<>();

        Bug bugToUpdate = bugService.findBugById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bug With Id = " + id + " Not Found."));

       //get list of multipart files and covert to attachment, add to attachments list
        Arrays.stream(files).forEach(file -> {
            try {
                if (!file.isEmpty() || !file.getContentType().equals("application/octet-stream")){ // prevent of uploading application/octet-stream MIME type is used for unknown binary files
                    BugAttachment fileToAdd = bugAttachmentService.convertMultipartToAttachment(file);
                    allAttachmentsForBugId.add(fileToAdd);
                    bugToUpdate.getBugAttachments().add(fileToAdd);
                }

            }catch (Exception e){
                e.getMessage();
            }
        });

       bugAttachmentService.saveAndFlush(allAttachmentsForBugId);

        model.addAttribute("allAttachments", allAttachmentsForBugId);
        model.addAttribute("messages", uploadMessages);
        return "redirect:/bugs/{id}/files";
    }

    //delete attachment
    @GetMapping("/bugs/{id}/files/{attachmentId}")
    public String deleteFile(@PathVariable("id") Long id, @PathVariable("attachmentId") Long attachmentId) {

        bugAttachmentService.delete(bugAttachmentService.getAttachment(attachmentId));
        return "redirect:/bugs/{id}/files";
    }

    //download attachment
    @GetMapping("/bugs/{id}/files/download/{attachmentId}")
    public ResponseEntity<byte[]> getFile(@PathVariable("id") Long id, @PathVariable("attachmentId") Long attachmentId) {
        BugAttachment file = bugAttachmentService.getAttachment(attachmentId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getAttachments());
    }
}



