package com.project.itservicedesk.dto;

import com.project.itservicedesk.models.Company;

import java.util.List;

public record ProjectInfoDTO(
        String projectName,
        Company company,
        String projectManagerName,
        List<String> usersAssignedToProject,
        Integer numberOfActiveTasks,
        Integer numberOfCompletedTasks,
        Integer numberOfActiveBugs,
        Integer numberOfCompletedBugs
) {
}
