package com.project.itservicedesk.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="bug_attachments")
public class BugAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @ManyToOne
    @JoinColumn(name = "bug_id", referencedColumnName = "id")
    private Bug bug;

    @Lob
    private byte[] attachments;
}
