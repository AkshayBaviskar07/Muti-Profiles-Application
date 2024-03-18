package com.telusko.MultProfilesApp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "COMPANY")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "STATE", nullable = false)
    private String state;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "COMPANY_CATEGORIES_TBL",
        joinColumns = {
                @JoinColumn(name = "company_id", referencedColumnName = "id")
        },
        inverseJoinColumns = {
                @JoinColumn(name = "category_id", referencedColumnName = "id")
        })
    private List<Category> categories;
}
