package com.example.gestionlivres.entity.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String title;
    private String author;
    private String description;
}
