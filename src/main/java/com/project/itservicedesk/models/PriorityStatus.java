package com.project.itservicedesk.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PriorityStatus {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    SEVERE("Severe");

    private final String displayLabel;
}
