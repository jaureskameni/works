package com.example.gestionlivres.service;

import com.example.gestionlivres.entity.dto.BookDto;
import com.example.gestionlivres.entity.dto.UpdateBookDto;

import java.util.List;

public interface BookService {
    void createBook(BookDto book);

    List<BookDto> findAllBook();

    BookDto findById(Integer id);

    void update(Integer id, BookDto bookDto);

    void setByUpdateBook(Integer id, UpdateBookDto updateBookDto);


    void deleteBook(Integer id);
}
