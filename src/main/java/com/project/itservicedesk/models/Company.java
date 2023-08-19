package com.project.itservicedesk.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString(of = "companyName")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @Column(name = "Company_name", nullable = false, unique = true)
    private String companyName;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "company")
    private Set<Project> projects;


}
