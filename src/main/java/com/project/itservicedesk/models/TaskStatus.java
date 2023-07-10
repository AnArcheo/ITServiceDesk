package com.project.itservicedesk.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaskStatus {
    NOT_STARTED("Not Started"),
    IN_PROGRESS("In Progress"),
    POSTPONED("Postponed"),
    COMPLETED("Completed");

    private final String displayLabel;
}
