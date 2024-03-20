package com.telusko.MultProfilesApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy=MM-dd")
    @Temporal(value = TemporalType.DATE)
    private String mfgDate;


    @Column(name = "EXPIRY_DATE", nullable = false, columnDefinition = "Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy=MM-dd")
    @Temporal(value = TemporalType.DATE)
    private String expiryDate;

    @ManyToOne
    private Category category;

    public Product(String name, Double price, String mfgDate, String expiryDate, Category category) {
        this.name = name;
        this.price = price;
        this.mfgDate = mfgDate;
        this.expiryDate = expiryDate;
        this.category = category;
    }
}
