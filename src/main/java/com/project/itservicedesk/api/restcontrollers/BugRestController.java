package com.project.itservicedesk.api.restcontrollers;

import com.project.itservicedesk.models.Bug;
import com.project.itservicedesk.models.BugStatus;
import com.project.itservicedesk.models.PriorityStatus;
import com.project.itservicedesk.services.BugService;
import jakarta.annotation.Priority;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BugRestController {
    private final BugService bugService;

    @GetMapping("/bugs")
    public ResponseEntity<List<Bug>> getAllBugs(@RequestParam(required = false) String title) {
        try {
            List<Bug> bugs = bugService.getAllBugs();


            if (bugs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(bugs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bugs/{id}")
    public ResponseEntity<Bug> getBugById(@PathVariable("id") long id) {
        Optional<Bug> bug = bugService.findBugById(id);

        if (bug.isPresent()) {
            return new ResponseEntity<>(bug.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/bugs")
    public ResponseEntity<Bug> createBug(@RequestBody Bug bug, @RequestParam(name = "status", required = false) BugStatus status,
                                         @RequestParam(name = "priority", required = false) PriorityStatus priority) {
        try {
            Bug newBug = new Bug();
            newBug.setTitle(bug.getTitle());
            newBug.setDescription(bug.getDescription());

            if (status == null) {
                newBug.setStatus(BugStatus.NOT_STARTED);
            }
            newBug.setStatus(bug.getStatus());
            if (priority == null) {
                newBug.setPriority(PriorityStatus.LOW);
            }
            newBug.setPriority(bug.getPriority());

            bugService.save(bug);

            return new ResponseEntity<>(newBug, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/bugs/{id}")
    public ResponseEntity<Bug> updateBug(@PathVariable("id") long id, @RequestBody Bug bug,
                                         @RequestParam(name = "status", required = false) BugStatus status,
                                         @RequestParam(name = "priority", required = false) PriorityStatus priority) {

        Optional<Bug> foundBug = bugService.findBugById(id);

        if (foundBug.isPresent()) {
            Bug updateBug = foundBug.get();
            updateBug.setTitle(bug.getTitle());
            updateBug.setDescription(bug.getDescription());
            if (status == null) {
                updateBug.setStatus(BugStatus.NOT_STARTED);
            }
            updateBug.setStatus(bug.getStatus());
            if (priority == null) {
                updateBug.setPriority(PriorityStatus.LOW);
            }
            updateBug.setPriority(bug.getPriority());
            return new ResponseEntity<>(bugService.save(updateBug), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/bugs/{id}")
    public ResponseEntity<HttpStatus> deleteBug(@PathVariable("id") long id) {
        try {
            bugService.delete(bugService.findBugById(id).get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/bugs")
    public ResponseEntity<HttpStatus> deleteAllBugs() {
        try {
            bugService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
