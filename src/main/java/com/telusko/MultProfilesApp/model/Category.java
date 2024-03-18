package com.telusko.MultProfilesApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "CATEGORIES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "LAST_NAME", nullable = false)
    private String type;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products;

    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private List<Company> companies;
}
