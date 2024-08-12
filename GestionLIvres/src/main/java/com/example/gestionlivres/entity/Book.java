package com.example.gestionlivres.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t-book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_title")
    private String title;

    @Column(name = "c_author")
    private String author;

    @Column(name = "description")
    private String description;
}
