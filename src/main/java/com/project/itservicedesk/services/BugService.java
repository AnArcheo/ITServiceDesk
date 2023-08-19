package com.project.itservicedesk.services;

import com.project.itservicedesk.models.Bug;
import com.project.itservicedesk.models.BugStatus;
import com.project.itservicedesk.models.PriorityStatus;
import com.project.itservicedesk.models.Task;
import com.project.itservicedesk.repositories.BugRepository;
import com.project.itservicedesk.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BugService {

    private final BugRepository bugRepository;

    public List<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    public Optional<Bug> findBugById(Long id) {
        return bugRepository.findById(id);
    }

    public Bug save(Bug bug) {
        if (bug.getId() == null) {
            bug.setCreatedDate(LocalDateTime.now());
        }
        bug.setModifiedDate(LocalDateTime.now());
        return bugRepository.save(bug);
    }

    public void delete(Bug bug) {
        bugRepository.delete(bug);
    }


    public Page<Bug> searchRepository(String keyword, Pageable pageable) {
        return bugRepository.searchRepository(keyword, pageable);
    }

    /**
     * Methods to return Pagination
     */
    public Page<Bug> getAll(Pageable pageable) {
        return bugRepository.findAll(pageable);
    }

    public List<Bug> getAllBugssCreatedOrAssignedTo(Long id) {
        return bugRepository.findAllByAssignedToOrCreatedBy(id);
    }



    public List<Bug> findByTitle(String title) {
        return bugRepository.findByTitle(title);
    }

    public void deleteAll() {
        bugRepository.deleteAll();
    }


}
