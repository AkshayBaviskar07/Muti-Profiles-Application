package com.telusko.MultProfilesApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "PRODUCTS")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_CODE")
    private Long code;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Column(name = "MFG_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date mfgDate;

    @Column(name = "EXPIRY_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @ManyToOne
    private Category category;

    public Product(String name, Double price,  Date mfgDate, Date expiryDate, Category category ) {
        this.name = name;
        this.price = price;
        this.mfgDate = mfgDate;
        this.expiryDate = expiryDate;
        this.category = category;
    }
}
