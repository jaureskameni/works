package com.example.gestionlivres.entity.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBookDto {

    private String title;
    private String description;


}
