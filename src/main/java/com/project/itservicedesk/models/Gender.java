package com.project.itservicedesk.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    FEMALE("Female"),
    MALE("Male");

    private final String displayLabel;
}
