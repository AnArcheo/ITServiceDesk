package com.project.itservicedesk.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BugStatus{
    NOT_STARTED("Not Started"),
    IN_PROGRESS("In Progress"),
    OPEN("Open"),
    TO_BE_TESTED("To Be Tested"),
    REVIEWING("Reviewing"),
    POSTPONED("Postponed"),
    NOT_AN_ISSUE("Not an Issue"),
    NOT_A_BUG("Not a Bug"),
    CANCELLED("Cancelled"),
    RESOLVED("Resolved"),
    CLOSED("Closed");

    private final String displayLabel;
}
