package com.project.itservicedesk.services;

import com.project.itservicedesk.models.Task;
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
public class TaskService {

    private final TaskRepository taskRepository;


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }


    public Task save(Task task) {
        if (task.getId() == null) {
            task.setCreatedDate(LocalDateTime.now());
        }
        task.setModifiedDate(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public void delete(Task task) {
        taskRepository.delete(task);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    public Page<Task> searchRepository(String keyword, Pageable pageable) {
        return taskRepository.searchRepository(keyword, pageable);
    }


    /**
     * Methods to return Pagination
     */
    public Page<Task> getAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public List<Task> getAllTasksCreatedOrAssignedTo(Long id) {
        return taskRepository.findAllByAssignedToOrCreatedBy(id);
    }


}
