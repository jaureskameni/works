package com.example.gestionlivres.mapper;

import com.example.gestionlivres.entity.Book;
import com.example.gestionlivres.entity.dto.BookDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title")
    @Mapping(target = "author")
    @Mapping(target = "description")
    BookDto toDto(Book book);

    @Mapping(target = "title")
    @Mapping(target = "author")
    @Mapping(target = "description")
    Book toEntity(BookDto bookDto);
}
