package com.project.itservicedesk.controllers;

import com.project.itservicedesk.models.Task;
import com.project.itservicedesk.models.TaskAttachment;
import com.project.itservicedesk.services.TaskAttachmentService;
import com.project.itservicedesk.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskController {
    private static final int[] PAGE_SIZES = { 5, 10, 15, 25 };

    private final TaskService taskService;
    private final TaskAttachmentService taskAttachmentService;
    @GetMapping("/tasks")
    public String showAllTasksWithPagingAndSorting(Model model,
                                                   @RequestParam(required = false) String keyword,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                   @RequestParam(defaultValue = "id,asc") String[] sort) {

        try{
            List<Task> tasks;
            String sortField = sort[0];
            String sortDirection = sort[1];

            Sort.Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort.Order order = new Sort.Order(direction, sortField);

            Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(order));

            Page<Task> pageTasks;

            if(keyword == null){
                pageTasks = taskService.getAll(pageable);
            }else{
                pageTasks = taskService.searchRepository(keyword, pageable);
                if (pageTasks.isEmpty()){
                    model.addAttribute("message", "No Tasks Found!!!");
                }
            }
            // model.addAttribute("taskList", taskService.getAllTasks());
            tasks = pageTasks.getContent();
            List<TaskAttachment> attachments = taskAttachmentService.getAllAttachments().toList();

            model.addAttribute("taskList", tasks);
            model.addAttribute("attachments", attachments);
            model.addAttribute("currentPage", pageTasks.getNumber() + 1);
            model.addAttribute("totalItems", pageTasks.getTotalElements());
            model.addAttribute("totalPages", pageTasks.getTotalPages());
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pageSizesList", PAGE_SIZES);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        }catch(Exception e){
            model.addAttribute("message", e.getMessage());
        }

        return "tasks";
    }
//TODO: get mapping showing details of task with comments and images
    @GetMapping("/tasks/details/{id}")
    public String showTaskDetails(@PathVariable("id") Long id, Model model){
        model.addAttribute("task", taskService.findTaskById(id));
        //TODO: include comment Service
        //TODO: include Attachments service
        return "taskDetails";//TODO: create html
    }



    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id){
//        taskService.delete(taskService.findTaskById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Task does not exist")));
        taskService.deleteById(id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTask(@PathVariable("id") Long id, Model model){
        model.addAttribute("task", taskService.findTaskById(id));
        return "editTaskForm";
    }

    //Attachments
    //open form to add attachment

    //TODO: upload file only for selected task id {id} -> move this to TaskFormController
    @GetMapping("/tasks/{id}/files/new")
    public String addAttachment(@PathVariable("id") Long id, Model model){
        model.addAttribute("task", taskService.findTaskById(id));
        return "upload_attachment_form";
    }

    @GetMapping("/tasks/{id}/files/show")
    public String showTaskAttachments(@PathVariable("id") Long id, Model model){
        model.addAttribute("attachments", taskAttachmentService.getAllAttachmentsForTaskId(id));
        return "showAllAttachments"; //TODO: create html
    }

    @PostMapping("/tasks/{id}/files/upload")
    public String uploadAttachments(@PathVariable("id") Long id, Model model, @RequestParam("files") MultipartFile[] files){
        //create list of info about failed and successful uploads
        List<String> uploadMessages = new ArrayList<>();

        Arrays.stream(files).forEach(file -> {
            try {
                taskAttachmentService.saveAttachment(file);
                uploadMessages.add(file.getOriginalFilename() + "[[ Upload SUCCESSFUL ]]");
            }catch (Exception e){
                uploadMessages.add(file.getOriginalFilename() + "[[ Upload FAILED ]]" + e.getMessage());
            }
        });

        model.addAttribute("messages", uploadMessages);
        return "upload_attachment_form";
    }



}
